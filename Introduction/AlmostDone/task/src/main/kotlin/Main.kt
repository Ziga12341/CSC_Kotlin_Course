fun main() {
    // Write your solution in this file
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
    TODO()
}

fun applySquaredFilter(picture: String): String {
    TODO()
}