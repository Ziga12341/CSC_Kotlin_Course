fun main() {
    println(getGameRules(4, 3, "ACEB", "ABCDEFGH"))
    playGame(generateSecret(4, "ABCDEFGH"), 4, 3, "ABCDEFGH")
}

fun getGameRules(wordLength: Int, maxAttemptsCount: Int, secretExample: String, alphabet: String): String {
    return """Welcome to the game! 

Two people play this game: one chooses a word (a sequence of letters), the other guesses it. In this version, the computer chooses the word: a sequence of $wordLength letters (for example, $secretExample). The user has several attempts to guess it (the max number is $maxAttemptsCount). For each attempt, the number of complete matches (letter and position) and partial matches (letter only) is reported.  The possible symbols in the word: $alphabet. 
    
For example, with $secretExample as the hidden word, the BCDF guess will give 1 full match (C) and 1 partial match (B)."""
}
fun generateSecret(wordLength:Int, alphabet: String): String {
    return List(wordLength) {alphabet.random()}.joinToString("")
}

fun playGame(secret: String, wordLength: Int, maxAttemptsCount: Int, alphabet: String) {
    var attemptsCounter = 0
    var guess = ""
    do {
        guess = safeUserInput(wordLength, guess)
        if (!isCorrectInput(guess, wordLength, alphabet)){
            break
        }
        attemptsCounter++
        if (isWin(isComplete(secret, guess), attemptsCounter, attemptsCounter)) {
            printRoundResults(secret, guess)
            println("Congratulations! You guessed it!")
            break
        }
        printRoundResults(secret, guess)
        if (isLoss(isComplete(secret, guess), attemptsCounter, maxAttemptsCount)) {
            println("Sorry, you lost! :( My word is $secret")
        }
    } while (attemptsCounter < maxAttemptsCount + 1)
}

fun safeUserInput(wordLength: Int, alphabet: String): String {
    println("Please input your guess. It should be of length $wordLength, and each symbol should be from the alphabet: $alphabet.")
    return safeReadLine()
}

fun isCorrectInput(userInput:String, wordLength:Int, alphabet:String):Boolean{
    if (userInput.length != wordLength){
        println("The length of your guess should be $wordLength characters! Try again!")
        return false
    }
    for (char in userInput){
        if (!alphabet.contains(char)){
            println("All symbols in your guess should be the $alphabet alphabet characters! Try again!")
            return false
        }
    }
    return true
}

fun isWin(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean {
    return complete && (attempts <= maxAttemptsCount + 1)
}

fun isLoss(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean {
    return !complete && (maxAttemptsCount + 1 <= attempts)
}

fun printRoundResults(secret: String, guess: String) {
    println(
        "Your guess has ${
            countExactMatches(
                secret,
                guess
            )
        } full matches and ${countPartialMatches(secret, guess)} partial matches."
    )
}

fun isComplete(secret: String, guess: String): Boolean {
    return secret == guess
}

fun countPartialMatches(secret: String, guess: String): Int {
    var counter = 0
    val removeLetterIndexes = mutableListOf<Int>()
    val changedSecretsList = secret.toMutableList()
    val changedGuessList = guess.toMutableList()

    for ((index, letter) in guess.withIndex()) {
        if (secret[index] == letter) {
            removeLetterIndexes += index
        }
    }
    val sortedIndices = removeLetterIndexes.sortedDescending()

    for (index in sortedIndices) {
        if (index in 0 until changedSecretsList.size) {
            changedSecretsList.removeAt(index)
            changedGuessList.removeAt(index)
        }
    }

    for (letter in changedSecretsList) {
        if (charInWord(letter, changedGuessList.joinToString(""))) {
            counter++
        }
    }

    return counter
}

//This function is case sensitive!!!
fun charInWord(letter: Char, word: String): Boolean {
    return letter in word
}

fun countExactMatches(secret: String, guess: String): Int {
    var matches = 0
    for ((index, symbol) in guess.withIndex()) {
        if (secret[index] == symbol) {
            matches++
        }
    }
    return matches
}