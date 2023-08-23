fun main() {
//    println("    ┈┈╱┈┈▔▔╲▂▂╱╲┈┈┈".length)
//    applyBordersFilter(simba)

}

fun trimPicture(picture: String): String {
    return picture.trimIndent()
}

fun applyFilter(trimmedPicture: String, filterName: String): String {
    return when (filterName) {
        "applyBordersFilter" -> applyBordersFilter(trimmedPicture)
        "applySquaredFilter" -> applySquaredFilter(trimmedPicture)
        else -> "This filter not implemented yet"
    }
}

fun applyBordersFilter(picture: String): String {
    val startOfEachLine: String = borderSymbol.toString() + separator.toString()
    val endOfEachLine: String =
        separator.toString() + borderSymbol.toString() + newLineSymbol.toString()

//   longest line length
    val maxLineLength = picture.lines().maxByOrNull { it.length }!!.length
//    this is important for top and bottom line
//    maybe i need to do + 4 for boarder
    val topBottomBorderSymbols: String = "$borderSymbol".repeat(maxLineLength + 4) + newLineSymbol
//    println(topBottomBorderSymbols)
//    println("new line symbol is $newLineSymbol")
//    println("top or bottom line is: $topBottomBorderSymbols")

//    you need to add "# " at beginning and " #" at the end of each line
//    you need to add whitespaces where needed
//    read line and add what needed to be add
    val allLinesInList = picture.lines()
    val allRelevantLines = allLinesInList
//        .drop(1).dropLast(1)

//    println("each line is: $allLinesInList")
//    println("each new line is: $allRelevantLines")
    val newPictureBody = StringBuilder()
    newPictureBody.append(topBottomBorderSymbols)
    for (line in allRelevantLines) {
//        specify how much whitespaces you need at the end and at tha beginning of each line
        val whiteSpacesPerLine: String =
            separator.toString().repeat((maxLineLength - line.length) / 2)
        newPictureBody.append(startOfEachLine + whiteSpacesPerLine + line + whiteSpacesPerLine + endOfEachLine)
    }
    newPictureBody.append(topBottomBorderSymbols)
//    println("new picture is: $newPictureBody")
    return newPictureBody.toString()
}

fun applySquaredFilter(picture: String): String {
    TODO()
}