fun main() {
    applyBordersFilter("sll\nks\ns\nllll\n")
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
//    how long is longest line
    println(picture.lines().sortedBy { it.length }.last().length)

    borderSymbol
    newLineSymbol
    return picture
}

fun applySquaredFilter(picture: String): String {
    TODO()
}