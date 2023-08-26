fun main() {
    photoshop()
}

fun photoshop(){
    val userChoicePicture = getPicture()
    val filter = chooseFilter()
    val pictureWithFilter = applyFilter(trimPicture(userChoicePicture), filter)
    println("The old image:\n$userChoicePicture")
    println("The transformed picture:\n$pictureWithFilter")
}


fun getPicture(): String {
    println("Do you want to use a pre-defined picture or use a custom one? Please, input 'yes' for a pre-defined image or 'no' for a custom one")
    do {
        when (safeReadLine()) {
            "yes" -> {
                return choosePicture()
            }
            "no" -> {
                println("Please, input a custom picture")
                return safeReadLine()
            }

            else -> println("Please, input 'yes' or 'no'")
        }
    } while (true)
}

fun choosePicture(): String {
    do {
        println("Please, choose a picture. The possible options: ${allPictures().joinToString (", ")}")
        val pictureName = safeReadLine()
        val picture = getPictureByName(pictureName)
        picture?.let {
            return picture
        }
    } while (true)
}

fun chooseFilter(): String {
    var filter = ""
    println("Please choose the filter: 'borders' or 'squared'.")
    var correctInput = false

    do {
        when (val filterName = safeReadLine()) {
            "borders", "squared" -> {
                filter = filterName
                correctInput = true
            }

            else -> {
                println("Please input 'borders' or 'squared'")
            }
        }
    } while (!correctInput)
    return filter
}

fun safeReadLine(): String {
    val userInput: String? = readlnOrNull()
    return userInput?: error("Please insert correct input")
}

fun trimPicture(picture: String): String {
    return picture.trimIndent()
}


fun applyFilter(trimmedPicture: String, filterName: String): String {
    return when (filterName) {
        "borders", "applyBordersFilter" -> applyBordersFilter(trimmedPicture)

        "squared", "applySquaredFilter" -> applySquaredFilter(trimmedPicture)

        else -> {
            println("filterName: $filterName")
            println("Char code: " + trimmedPicture[0].code)
            println("Char: " + trimmedPicture[0])
            println("trimmedPicture:\n $trimmedPicture")
            "This filter not implemented yet"
        }
    }
}

fun applyBordersFilter(picture: String): String {
    // Char + Char -> String
    val startOfEachLine: String = borderSymbol.toString() + separator.toString()
    val endOfEachLine: String =
        separator.toString() + borderSymbol.toString() + newLineSymbol.toString()

//   longest line length
    val maxLineLength = picture.lines().maxByOrNull { it.length }!!.length
//    this is important for top and bottom line
//    maybe i need to do + 4 for boarder
    val topBottomBorderSymbols: String =
        "$borderSymbol".repeat(maxLineLength + 4) + newLineSymbol

//    you need to add "# " at beginning and " #" at the end of each line
//    you need to add whitespaces where needed
//    read line and add what needed to be add
    val allRelevantLines = picture.lines()

//    compute changed (bordered) picture line by line
    val newPictureBody = StringBuilder()
    newPictureBody.append(topBottomBorderSymbols)

    for (line in allRelevantLines) {
//        specify how much whitespaces you need at the end and at the beginning of each line
        val whiteSpacesPerLine: String =
            separator.toString().repeat((maxLineLength - line.length) / 2)
        val addBorderToPictureStart = startOfEachLine + line + whiteSpacesPerLine
//        on the end of each line add as many separators as needed to fulfill whitespace and end of each line " #"
//        to max line length i needed to add 2 more because i already added "# "
        val whitespaceToAddAtTheEnd = maxLineLength + 2 - addBorderToPictureStart.length
        val addBorderToPictureEnd =
            separator.toString().repeat(whitespaceToAddAtTheEnd) + endOfEachLine

        newPictureBody.append(addBorderToPictureStart + addBorderToPictureEnd)
    }
//    append each line
    newPictureBody.append(topBottomBorderSymbols)
    return newPictureBody.toString()
}

fun applySquaredFilter(picture: String): String {
    val borderWithoutLast = applyBordersFilter(picture)
    val first = StringBuilder()
    val second = StringBuilder()

    val lines = borderWithoutLast.lineSequence().toList()
    for (i in 0 until lines.size - 1) { // Skip the last line
        first.append(lines[i]).append(lines[i]).append(newLineSymbol)
    }
//    other.append(newLineSymbol)
    for (line in lines.drop(1)) {
        second.append(line).append(line).append(newLineSymbol)
    }

    return first.toString() + second.dropLast(1).toString()
}