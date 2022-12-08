fun getResourceAsLines(path: String): List<String> =
    getResourceAsText(path).lines()

fun getResourceAsText(path: String): String =
    object {}.javaClass.getResource(path).readText()


fun array2dToString(a:List<List<Any>>):String{
    return a.joinToString("\n") { it.joinToString("") }
}
fun array2dToString(a:Array<Array<Any>>):String{
    return a.joinToString("\n") { it.joinToString("") }
}

fun linesToMatrix(lines: List<String>):List<List<Int>> = lines.map{ line->
    line.map {  it.digitToInt()  }
}
/*
Extensions
 */
fun CharSequence.splitIgnoreEmpty(regex : Regex): List<String> {
    return this.split(regex).filter {
        it.isNotEmpty()
    }
}

fun Iterable<String>.mapToInts(): List<Int> {
    return this.map{it.toInt()}
}
