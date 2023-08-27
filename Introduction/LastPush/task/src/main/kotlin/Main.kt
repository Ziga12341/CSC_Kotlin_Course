fun main() {
//    println(fillPatternRow("|-|", 6))
//    println(getPatternHeight("123\n123\n123"))
//    println(dropTopFromLine("123\n123\n123\n"))
//    println(repeatHorizontally("123\n", 5))
//    println(canvasGenerator("123$newLineSymbol$separator"+"2$separator", 2, 3))
    println(canvasGenerator("○", 1, 2))
}

fun canvasGenerator(pattern: String, width: Int, height: Int): String {
    var generatedCanvasPicture = ""
    if (height == 1) {
        generatedCanvasPicture = repeatHorizontally(pattern, width)
    } else if (height < 1) {
        println("Error: height can not be less than 1")
    } else {
//        case where we need to repeat lines and drop first
//        firstly just copy first line
        generatedCanvasPicture += repeatHorizontally(pattern, width) + newLineSymbol
        for (i in 0 until height - 1) {
            println("just print i: $i")
            if (getPatternHeight(pattern) > 1) {
                generatedCanvasPicture += repeatHorizontally(dropTopFromLine(pattern), width) + newLineSymbol
            } else if (getPatternHeight(pattern) == 1) {
//        do not remove top line if height more than 1
                generatedCanvasPicture += repeatHorizontally(pattern, width) + newLineSymbol
            } else {
                println("error; pattern has no lines")
            }
        }
    }
    return generatedCanvasPicture

}

fun dropTopFromLine(pattern: String): String {
    return pattern.lines().drop(1).joinToString(newLineSymbol)
}

fun repeatHorizontally(pattern: String, repeat: Int): String {
    return pattern.repeat(repeat)
}

fun fillPatternRow(patternRow: String, patternWidth: Int): String {
//    add separator to row
//    Please, throw an IllegalStateException if patternRow.length > patternWidth.
    if (patternRow.length > patternWidth) throw IllegalStateException() else {
        return patternRow + separator.toString().repeat(patternWidth - patternRow.length)
    }
}

fun getPatternHeight(pattern: String): Int {
//    calculates pattern height
//    convert pattern into list line by line ang get its size
    return pattern.lines().size
}