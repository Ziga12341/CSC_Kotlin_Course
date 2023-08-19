fun main() {
    println(getGameRules(4, 3, "ACEB"))
    playGame(generateSecret(), 4, 3)
}

fun getGameRules(wordLength: Int, maxAttemptsCount: Int, secretExample: String): String {
    return """Welcome to the game! 

Two people play this game: one chooses a word (a sequence of letters), the other guesses it. In this version, the computer chooses the word: a sequence of $wordLength letters (for example, $secretExample). The user has several attempts to guess it (the max number is $maxAttemptsCount). For each attempt, the number of complete matches (letter and position) and partial matches (letter only) is reported.

For example, with $secretExample as the hidden word, the BCDF guess will give 1 full match (C) and 1 partial match (B)."""
}

fun playGame(secret: String, wordLength: Int, maxAttemptsCount: Int) {
    var attemptsCounter = 0
    var guess = ""
    do {
        println("Please input your guess. It should be of length $wordLength.")
        guess = safeReadLine()
        attemptsCounter++
        isComplete(secret, guess)
    } while (maxAttemptsCount < attemptsCounter)
}

fun isComplete(secret: String, guess: String): Boolean {
    return secret === guess
}
fun generateSecret(): String {
    return "ABCD"
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
//why type Char do not work for letter "a" or "A"... i need to put type String here
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