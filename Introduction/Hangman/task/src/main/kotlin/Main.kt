fun main() {
//    var currentUserWordList = "____".toMutableList()
//    println(currentUserWordList.set(0, 'B'))
//    println(currentUserWordList.joinToString(""))
//    var currentUserWord: String
    println(generateNewUserWord("BOOK", 'K', "___K"))
}

//    TODO("specify what if user guessed right")
//        change current user word the way that you add guessed leter or more of them to currentUserWord
//        change index in curent user word from "_" to guess

fun generateNewUserWord(secret: String, guess: Char, currentUserWord: String): String {
    var newCurrentUserWord = currentUserWord
    val newCurrentUserWordList = currentUserWord.toMutableList()
    if (guess in currentUserWord){
        return newCurrentUserWord
    }
    if (guess in secret) {
        for ((index, symbol) in secret.withIndex()) {
            if (symbol == guess) {
                newCurrentUserWordList[index] = symbol
                newCurrentUserWord = newCurrentUserWordList.joinToString("")
            }
        }
    } else {
        println("Sorry, the secret does not contain the symbol: $guess. The current word is: $currentUserWord")
    }

    return newCurrentUserWord
}

fun isComplete(secret: String, currentGuess: String): Boolean{
    return secret == currentGuess
}