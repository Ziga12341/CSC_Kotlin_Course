fun main() {
    println(applyFilter(trimPicture(android), "applySquaredFilter"))
//    println("    ┈┈╱┈┈▔▔╲▂▂╱╲┈┈┈".length)
//    println(applyBordersFilter(android))
//    println(android)
//    println( applyFilter(trimPicture(android), "applyBordersFilter"))

}

fun trimPicture(picture: String): String {
    return picture.trimIndent()
}


fun applyFilter(trimmedPicture: String, filterName: String): String {
    return when (filterName) {
        "borders",
        "applyBordersFilter" -> applyBordersFilter(trimmedPicture)

        "square",
        "applySquaredFilter" -> applySquaredFilter(trimmedPicture)

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
    val topBottomBorderSymbols: String = "$borderSymbol".repeat(maxLineLength + 4) + newLineSymbol

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
        val addBorderToPictureEnd = separator.toString().repeat(whitespaceToAddAtTheEnd) + endOfEachLine

        newPictureBody.append(addBorderToPictureStart + addBorderToPictureEnd)
    }
//    append each line
    newPictureBody.append(topBottomBorderSymbols)
    return newPictureBody.toString()
}

fun applySquaredFilter(picture: String): String {
    var newPictureBodyTop = StringBuilder()
    var newPictureBodyBottom = StringBuilder()
    var borderWithoutLast = applyBordersFilter(picture)
    var borderWithoutLastList = borderWithoutLast.toMutableList()
    var borderWithoutLastListRemoved = borderWithoutLastList.removeLast()

    for (line in borderWithoutLastList) {
            val mergeLine = ("$line$line")
//            println(mergeLine)
            newPictureBodyTop.append(line)
            newPictureBodyBottom.append(line)

    }
    val result = StringBuilder()
    println((newPictureBodyTop.toMutableList() + newPictureBodyTop.toMutableList()).joinToString(""))
//    result.append(newPictureBodyBottom.toString())
//    result.append(newPictureBodyBottom.toString())

    return  result.toString()
}