fun main() {
    val word:String = words.random()
    val secretWordUnderscored:String = (underscore+separator).repeat(word.length)
    gameRules(maxAttemptsCount, word.length)
    println("I guessed the word: $secretWordUnderscored")
    do {
        var guess:Char = safeUserInput()
        var secretWordUnderscored = generateNewUserWord(word, guess, secretWordUnderscored)

    } while (isComplete(word, secretWordUnderscored))

}

fun gameRules(maxAttemptsCount: Int, wordLength: Int) {
    println(
        "Welcome to the game!\n" +
                "\n" +
                "In this game, you need to guess the word chosen by the computer.\n" +
                "The hidden word will appear as a sequence of underscore characters, one per letter.\n" +
                "You have $maxAttemptsCount attempts to guess the word.\n" +
                "All words are English words, consisting of $wordLength letters.\n" +
                "At each attempt, you should enter one letter; \n" +
                "if it is present in the hidden word, all matches will be displayed.\n" +
                "\n" +
                "For example, if the word CAT was chosen by the computer, _ _ _ will be displayed first,\n" +
                "since the word has 3 letters.\n" +
                "If you enter the letter A, you will see _ A _, and so on.\n" +
                "\n" +
                "Good luck with the game!"
    )
}

//chat GPT answer use while loop... asking user until he input something what is not null
fun safeUserInput(): Char {
    var guess: String?
    println("Please input your guess.")
    do {
        guess = readLine()
    } while (guess == null || !isCorrectInput(guess))
    return guess[0].uppercaseChar()
}

//my function - do not pass teats because it is case when user input is null...
fun safeUserInput2(): Char {
    val guess: String = readlnOrNull().toString()
    return guess.map { it.uppercase() }.filter { isCorrectInput(guess) }.joinToString("").toCharArray()[0]
}


fun isCorrectInput(guess: String): Boolean {
//    check if guess (which user provide) is only one char
    if (guess.length != 1) {
        println("The length of your guess should be 1! Try again!")
        return false
    }
//    check user provide english letter in guess
//    i could use isLetter
    if (!(guess >= "a" && guess <= "z" || guess >= "A" && guess <= "Z")) {
        println("You should input only English letters! Try again!")
        return false
    }
    return true
}

fun generateNewUserWord(secret: String, guess: Char, currentUserWord: String): String {
    var newCurrentUserWord = currentUserWord
    val newCurrentUserWordList = currentUserWord.toMutableList()
//    case if already guessed symbol
    if (guess in currentUserWord) {
        return newCurrentUserWord
    }
//    case if user guessed right
    if (guess in secret) {
        for ((index, symbol) in secret.withIndex()) {
            if (symbol == guess) {
//                on index where user guessed right change "_" with symbol
                newCurrentUserWordList[index] = symbol
                newCurrentUserWord = newCurrentUserWordList.joinToString("")
            }
        }
    } else {
        println("Sorry, the secret does not contain the symbol: $guess. The current word is: $currentUserWord")
    }

    return newCurrentUserWord
}

fun isComplete(secret: String, currentGuess: String): Boolean {
    return secret == currentGuess.replace("_", "").replace(" ", "")
}