fun main() {
}

fun canvasGenerator1(patternA: String, width: Int, height: Int): String {
    val pattern = mapProblemAtoProblemB(patternA, separator)

    var generatedCanvasPicture = ""
    val maxLineLength = 0 // pattern.lines().maxOf { it.length }

    if (height == 1) {
        generatedCanvasPicture = repeatHorizontally(pattern, width, maxLineLength)
        println("generatedCanvasPicture $generatedCanvasPicture")
    } else if (height < 1) {
        println("Error: height can not be less than 1")
    } else {
//        case where we need to repeat lines and drop first
//        firstly just copy first line
        generatedCanvasPicture += repeatHorizontally(pattern, width, maxLineLength) + newLineSymbol
        for (i in 0 until height - 1) {
//            println("just print i: $i")
            if (getPatternHeight(pattern) > 1) {
                generatedCanvasPicture += repeatHorizontally(dropTopFromLine(pattern), width, maxLineLength) + newLineSymbol
            } else if (getPatternHeight(pattern) == 1) {
//        do not remove top line if height more than 1
                generatedCanvasPicture += repeatHorizontally(pattern, width, maxLineLength) + newLineSymbol
            } else {
                println("error; pattern has no lines")
            }
        }
    }

    return generatedCanvasPicture
//    return mapProblemBtoProblemA(generatedCanvasPicture, separator)
}

fun canvasGenerator(pattern: String, width: Int, height: Int): String {
    var generatedCanvasPicture = ""
    val maxLineLength = pattern.lines().maxOf { it.length }

    if (height == 1) {
        generatedCanvasPicture = repeatHorizontally(pattern, width, maxLineLength)
        println("generatedCanvasPicture $generatedCanvasPicture")
    } else if (height < 1) {
        println("Error: height can not be less than 1")
    } else {
//        case where we need to repeat lines and drop first
//        firstly just copy first line
        // memoization <-- vsako pure funkcijo jo lahko samo enkrat pokličemo za uniqe set inputov
        // funkcijski jeziki ponavadi imajo memoization na voljo v jeziku
        val repeatedPattern = repeatHorizontally(pattern, width, maxLineLength) + newLineSymbol
        val nonFirstPatterns = repeatHorizontally(dropTopFromLine(pattern), width, maxLineLength) + newLineSymbol
        generatedCanvasPicture += repeatedPattern
        for (i in 0 until height - 1) {
//            println("just print i: $i")
            if (getPatternHeight(pattern) > 1) {
                generatedCanvasPicture += nonFirstPatterns
            } else if (getPatternHeight(pattern) == 1) {
//        do not remove top line if height more than 1
                generatedCanvasPicture += repeatedPattern
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

// pure funkcija odvisna od
// - inputov: pattern, repeat
// - konstante: separator, newLineSymbol
fun repeatHorizontally(pattern: String, repeat: Int, maxLineLength:Int): String {
    val listOfLines: List<String> = pattern.lines()
    println(listOfLines)
    val newListOfLines = mutableListOf<String>()
//    val maxLineLength = listOfLines.maxOf { it.length }
//    length of longest line in list
    for (line in listOfLines){
//        val addToEndOfLine = maxLineLength - line.length
//        println("padding per line: $addToEndOfLine")
        val modLine = line.padEnd(maxLineLength, separator)
        newListOfLines.add(modLine.repeat(repeat))
    }

    return newListOfLines.joinToString(newLineSymbol)
}

fun mapProblemAtoProblemB(pattern:String, separator: Char): String {
    val toListOfLines = pattern.lines()
    val max = toListOfLines.maxOf { it.length }
    return toListOfLines
        .joinToString(newLineSymbol) { it.padEnd(max, separator) }
}

fun mapProblemBtoProblemA(partialResult: String, separator: Char): String {
    val toListOfLines = partialResult.lines()
    return toListOfLines
        .joinToString(newLineSymbol) { it.trimEnd(separator) }
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