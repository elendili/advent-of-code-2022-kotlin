fun CharSequence.splitIgnoreEmpty(regex : Regex): List<String> {
    return this.split(regex).filter {
        it.isNotEmpty()
    }
}

fun Iterable<String>.mapToInts(): List<Int> {
    return this.map{it.toInt()}
}
