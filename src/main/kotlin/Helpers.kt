fun getResourceAsLines(path: String): List<String> =
    getResourceAsText(path).lines()

fun getResourceAsText(path: String): String =
    object {}.javaClass.getResource(path).readText()

fun CharSequence.splitIgnoreEmpty(regex : Regex): List<String> {
    return this.split(regex).filter {
        it.isNotEmpty()
    }
}

fun Iterable<String>.mapToInts(): List<Int> {
    return this.map{it.toInt()}
}
