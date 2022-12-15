fun getResourceAsLines(path: String): List<String> =
    getResourceAsText(path).lines()

fun getResourceAsText(path: String): String =
    object {}.javaClass.getResource(path).readText()


fun list2dToString(a:List<List<Any>>):String{
    return a.joinToString("\n") { it.joinToString("") }
}
fun array2dToString(a:Array<Array<*>>):String{
    return a.joinToString("\n") { it.joinToString("") }
}

fun linesToMatrix(lines: List<String>):List<List<Int>> = lines.map{ line->
    line.map {  it.digitToInt()  }
}

fun isDebug():Boolean = false

inline fun whenDebug(action: () -> Unit) {
    if(isDebug()) {
        action()
    }
}