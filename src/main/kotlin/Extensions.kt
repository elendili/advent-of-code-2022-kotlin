import java.math.BigDecimal

fun CharSequence.splitIgnoreEmpty(regex : Regex): List<String> {
    return this.split(regex).filter {
        it.isNotEmpty()
    }
}

fun Iterable<String>.mapToInts(): List<Int> {
    return this.map{it.toInt()}
}

/**
       val result : Int = "+".toIntOp()(2, 3)
 */
fun String.toIntOp(): Int.(Int) -> Int = when (this.trim()) {
    "+" -> Int::plus
    "-" -> Int::minus
    "*" -> Int::times
    "/" -> Int::div
    else -> error("Unknown operator $this")
}
fun String.toLongOp(): Long.(Long) -> Long = when (this.trim()) {
    "+" -> Long::plus
    "-" -> Long::minus
    "*" -> Long::times
    "/" -> Long::div
    else -> error("Unknown operator $this")
}
fun String.toBigDecimalOp(): BigDecimal.(BigDecimal) -> BigDecimal = when (this.trim()) {
    "+" -> BigDecimal::plus
    "-" -> BigDecimal::minus
    "*" -> BigDecimal::times
    "/" -> BigDecimal::div
    else -> error("Unknown operator $this")
}