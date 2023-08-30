import java.lang.StringBuilder

fun main() {
    println(
        canvasWithGapsGenerator(
            """ X
/ \
\ /
 X""", 5, 7
        )
    )
//    removeOddOrEvenFromHorizontallyRepeatedPattern(
//        """ X
/// \
//\ /
// X""", width = 5, 3, 1
//    )
//
//    println(charNeedChange(11, 3, 5, false))
//    println(charNeedChange(7, 3, 5, true))

//    println("for odds")
//    for (i in 0 until 15) {
//        println(charNeedChange(i, 3, true))
//    }
//    println("for even:")
//    for (i in 0 until 15) {
////        println(i)
//        println(charNeedChange(i, 3, false))
//    }

//    println( changeLine( " X  X  X  X  X ",3,true))

}
//  need to return new string of pattern canvas with gaps

fun canvasWithGapsGenerator(pattern: String, width: Int, height: Int): String {
//  longest line in petter
    val patternMaxWidth = pattern.lines().maxOf { it.length }
//  max high of pattern
    val patternMaxHigh = pattern.lines().size
    val gapedCanvasWithPattern = StringBuilder()
//  here you get repeated pattern in canvas without gaps:
    var canvasedPattern = ""
    for (i in 1..height) {
        canvasedPattern += repeatHorizontally(pattern, width, patternMaxWidth) + newLineSymbol
    }
//  remove last line
    val oneLineOfPatternInList = canvasedPattern.lines().dropLast(1)
    var counter = 0
    val chunkedCanvas = canvasedPattern.lines().chunked(patternMaxHigh)
    println(chunkedCanvas)
    if (height == 2) {
        gapedCanvasWithPattern.append(
            repeatHorizontally(
                pattern,
                width,
                patternMaxWidth
            ) + newLineSymbol
        )
        gapedCanvasWithPattern.append(
            repeatHorizontally(
                pattern,
                width,
                patternMaxWidth
            ) + newLineSymbol
        )
    } else {
        for ((index, innerList) in chunkedCanvas.withIndex()) {

//      for first line you always do not change first pattern or first line
            if (index % 2 == 0) {
                println("if: $index")
                println(innerList)
                for (line in innerList) {

                    gapedCanvasWithPattern.append(
                        changeLine(
                            line,
                            patternMaxWidth,
                            true
                        ) + newLineSymbol
                    )
                }
            } else {
                println("else: $index")
                println(innerList)
                for (line in innerList) {
                    gapedCanvasWithPattern.append(
                        changeLine(
                            line,
                            patternMaxWidth,
                            false
                        ) + newLineSymbol
                    )
                }
            }
        }
    }
    val gapedCanvasWithPatterNoLastLine = gapedCanvasWithPattern.dropLast(2)
    return gapedCanvasWithPatterNoLastLine.toString()
}

//  this function needs explanation:
//  it return list of booleans which tells if we need to change particular char in line or not
//  it takes only one line - function do not know where it is and pattern max size... do not need to know how big is string
//  this function is for odds only

fun charNeedChangeInOdds(line: String, patternMaxWidth: Int): List<Boolean> {
    val charByCharNeedChange = mutableListOf<Boolean>()
    val chunkedListOfLine = line.toList().chunked(patternMaxWidth)
    for ((index, innerList) in chunkedListOfLine.withIndex()) charByCharNeedChange +=
        if (index % 2 == 0) {
            (innerList.map { true })
        } else {
            (innerList.map { false })
        }
    return charByCharNeedChange
}

//  this function is for evens only


fun charNeedChangeInEvens(
    wholeCanvasWithNoWhitespace: String,
    patternMaxWidth: Int
): List<Boolean> {
    val charByCharNeedChange = mutableListOf<Boolean>()
    val chunkedListOfLine = wholeCanvasWithNoWhitespace.toList().chunked(patternMaxWidth)
    for ((index, innerList) in chunkedListOfLine.withIndex()) charByCharNeedChange +=
        if (index % 2 == 1) {
            (innerList.map { true })
        } else {
            (innerList.map { false })
        }
    return charByCharNeedChange
}

//  Write two functions which are similar than repeatHorizontally but except also Odd/Even numbers
//  With that info i will remove lines which represent every even or odd position of element


fun removeOddOrEvenFromHorizontallyRepeatedPattern(
    pattern: String,
    width: Int,
    patternMaxWidth: Int,
    patternHeightLine: Int
) {
//  remove odds - if int in for loop is odd remove every second pattern
    if (patternHeightLine % 2 == 1) {
        val oneLineOfPattern =
            (repeatHorizontally(pattern, width, patternMaxWidth) + newLineSymbol)
        val oneLineOfPatternInList =
            (repeatHorizontally(pattern, width, patternMaxWidth) + newLineSymbol).lines()
        println(oneLineOfPattern)
        println("this is first:")
//      last line is empty
        println(oneLineOfPattern.lines().first())
        for (line in oneLineOfPatternInList) {
            println(line.length)
        }
    }
}

//  this function will change char that needs to be changed - if we need to change it will tell us fun charNeedChange
fun changeLine(oneLineOfPattern: String, patternMaxWidth: Int, isOdd: Boolean): String {
    var changedLine = ""
    for ((index, char) in oneLineOfPattern.withIndex()) {
        changedLine += if (charNeedChange(index, patternMaxWidth, isOdd)) {
            separator
        } else {
            char
        }
    }
    return changedLine
}

fun charNeedChange(
    indexOfCharInLine: Int,
    patternMaxWidth: Int,
    isOdd: Boolean
): Boolean {
//    for all possible options
//  dividedIndex is from 0 to 5 in my tests cases
    val dividedIndex = indexOfCharInLine % (patternMaxWidth * 2)
    if (isOdd) {
//      false, false, false, true, true, true
//      if divided index is bigger than max width - return true
        if (dividedIndex + 1 > patternMaxWidth) {
            return true
        }
    } else {
//      case for even (like second row - at a beginning just spaces) - need to remove - replace with space
//      true, true, true, false, false, false
        if (dividedIndex + 1 <= patternMaxWidth) {
            return true
        }
    }
    return false
}


fun canvasGenerator1(patternA: String, width: Int, height: Int): String {
    val pattern = mapProblemAtoProblemB(patternA, separator)

    var generatedCanvasPicture = ""
    val maxLineLength = 0 // pattern.lines().maxOf { it.length }

    if (height == 1) {
        generatedCanvasPicture = repeatHorizontally(pattern, width, maxLineLength)
    } else if (height < 1) {
        println("Error: height can not be less than 1")
    } else {
//        case where we need to repeat lines and drop first
//        firstly just copy first line
        generatedCanvasPicture += repeatHorizontally(
            pattern,
            width,
            maxLineLength
        ) + newLineSymbol
        for (i in 0 until height - 1) {
            if (getPatternHeight(pattern) > 1) {
                generatedCanvasPicture += repeatHorizontally(
                    dropTopFromLine(pattern),
                    width,
                    maxLineLength
                ) + newLineSymbol
            } else if (getPatternHeight(pattern) == 1) {
//        do not remove top line if height more than 1
                generatedCanvasPicture += repeatHorizontally(
                    pattern,
                    width,
                    maxLineLength
                ) + newLineSymbol
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
    } else if (height < 1) {
        println("Error: height can not be less than 1")
    } else {
//        case where we need to repeat lines and drop first
//        firstly just copy first line
        // memoization <-- vsako pure funkcijo jo lahko samo enkrat pokličemo za uniqe set inputov
        // funkcijski jeziki ponavadi imajo memoization na voljo v jeziku
        val repeatedPattern = repeatHorizontally(pattern, width, maxLineLength) + newLineSymbol
        val nonFirstPatterns =
            repeatHorizontally(dropTopFromLine(pattern), width, maxLineLength) + newLineSymbol
        generatedCanvasPicture += repeatedPattern
        for (i in 0 until height - 1) {
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
fun repeatHorizontally(pattern: String, repeat: Int, maxLineLength: Int): String {
    val listOfLines: List<String> = pattern.lines()
    val newListOfLines = mutableListOf<String>()
//    length of longest line in list
    for (line in listOfLines) {
        val modLine = line.padEnd(maxLineLength, separator)
        newListOfLines.add(modLine.repeat(repeat))
    }

    return newListOfLines.joinToString(newLineSymbol)
}

fun mapProblemAtoProblemB(pattern: String, separator: Char): String {
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