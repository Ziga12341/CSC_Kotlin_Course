fun main() {
}

fun generateNewUserWord(secret: String, guess: Char, currentUserWord: String): String {
    var newCurrentUserWord = currentUserWord
    val newCurrentUserWordList = currentUserWord.toMutableList()
//    case if already guessed symbol
    if (guess in currentUserWord){
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

fun isComplete(secret: String, currentGuess: String): Boolean{
    return secret == currentGuess
}