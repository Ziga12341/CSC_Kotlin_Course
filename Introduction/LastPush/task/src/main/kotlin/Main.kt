fun main() {
    println(fillPatternRow("|-|", 6))
    println(getPatternHeight("123\n123\n123"))

}

fun fillPatternRow(patternRow:String, patternWidth:Int):String{
//    add separator to row
//    Please, throw an IllegalStateException if patternRow.length > patternWidth.
    if (patternRow.length > patternWidth) throw IllegalStateException() else {
        return patternRow + separator.toString().repeat(patternWidth - patternRow.length)
    }
}

fun getPatternHeight(pattern: String):Int{
//    calculates pattern height
//    convert pattern into list line by line ang get its size
    return pattern.lines().size
}