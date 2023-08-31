import java.lang.StringBuilder

fun main() {
    println("The possible options: ${allPatterns().joinToString(", ")}")

    println(
        canvasWithGapsGenerator(
            """ X
/ \
\ /
 X""", 5, 7
        )
    )

    println(
        applyGenerator(
            """0""", "canvas", 1, 2
        )
    )

}
//  need to return new string of pattern canvas with gaps

//This function should call the necessary generator to return a generated picture.
// !!!!!!!! this function do not passes some tests with low width and height number !!!!!!!!!!!
fun applyGenerator(pattern: String, generatorName: String, width: Int, height: Int): String {
    return when (generatorName) {
        "canvas" -> canvasGenerator(pattern, width, height)
        "canvasGaps" -> canvasWithGapsGenerator(pattern, width, height)
        else -> "invalid input, try again"
    }
}

// which asks if the user wants to choose a pre-defined pattern or input a custom one. This function returns the pattern which should be used for pattern generation.
fun getPattern(): String {
    var output: String = ""
    var userPattern = ""
    println(
        """Do you want to use a pre-defined pattern or a custom one?
Please input 'yes' for a pre-defined pattern or 'no' for a custom one."""
    )
    do {
        val answer = safeReadLine()
        if ((answer != "yes") || (answer != "no")){
            println("Please input 'yes' or 'no'")
        }
        else{userPattern = answer}
    } while (answer == "yes" || answer == "no")
    if (userPattern == "yes"){
        println("The possible options: ${allPatterns().joinToString(", ")}")
    }

        val width = safeReadLine().toIntOrNull() ?: error("Incorrect number!")
    return output
}

fun safeReadLine() = readlnOrNull() ?: error("Your input is incorrect, sorry")

fun canvasWithGapsGenerator(pattern: String, width: Int, height: Int): String {
//  longest line in petter
    val patternMaxWidth = pattern.lines().maxOf { it.length }
//  max high of pattern
    val patternMaxHigh = pattern.lines().size
//  this is final var that I return on the end - collecting changed line by line and at the end drop last line
    val gapedCanvasWithPattern = StringBuilder()

//  here you get repeated pattern in canvas without gaps:
    var canvasedPattern = ""
    for (i in 1..height) {
        canvasedPattern += repeatHorizontally(pattern, width, patternMaxWidth) + newLineSymbol
    }
//  remove last line
    canvasedPattern.lines().dropLast(1)

//  main functionality of this function
    val chunkedCanvas = canvasedPattern.lines().chunked(patternMaxHigh)
//  special case for canvas with high 2 --> When repeated vertically, the pattern remains unchanged.
//  append the some line two times
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
//  check if we need to change some char in particular line

        for ((index, innerList) in chunkedCanvas.withIndex()) {

//      for first line you always do not change first pattern or first line
//      we start with 1 not with 0 because it is first line
            val newIndex = index + 1
            //      is odd is always true because first line is odd
            if (newIndex % 2 == 1) {
                for (line in innerList) {
//              just use changeLine function
                    gapedCanvasWithPattern.append(
                        changeLine(
                            line,
                            patternMaxWidth,
                            true
                        ) + newLineSymbol
                    )
                }
            } else {
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
//  need to drop last new lines
    val gapedCanvasWithPatterNoLastLine = gapedCanvasWithPattern.dropLast(2)
    return gapedCanvasWithPatterNoLastLine.toString()
}

//  at the end i did not use this code
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

//  at the end i did not use this code
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