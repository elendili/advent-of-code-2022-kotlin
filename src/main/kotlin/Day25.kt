import kotlin.math.absoluteValue
import kotlin.math.pow

// https://adventofcode.com/2022/day/25
class Day25(val lines: List<String>) {
// make function static
    companion object {
        fun charToSnafuInteger(c: Char): Int {
            return when(c){
                '2' -> 2
                '1' -> 1
                '0' -> 0
                '-' -> -1
                '=' -> -2
                else -> throw Error("'$c' is not a snafu character")
            }
        }
        fun convertSnafuToInteger(s: String): Long {
            return s.reversed().mapIndexed{i, c ->
                (charToSnafuInteger(c).toLong() *  5.toDouble().pow(i)).toLong()
            }.sum()
        }

        fun convertIntegerToSnafu(number: Long): String {
            // get 3-base representation of number
            val number5Based = number.toString(5)
            // convert 3-base number to snafu
            val sb = StringBuilder()
            var carried = 0
//            "4890, 2=-1=0",
//            "314159265, 1121-1110-1=0"
            // "103, 1-1="
            number5Based.reversed().forEachIndexed{ i,c->
                val curV = c.digitToInt()+carried
                val v = when(curV){
                    5-> {
                        carried = 1
                        '0'
                    }
                    4-> {
                        carried = 1
                        '-'
                    }
                    3-> {
                        carried = 1
                        '='
                    }
                    2 -> {
                        carried = 0
                        '2'
                    }
                    1 -> {
                        carried = 0
                        '1'
                    }
                    0 -> {
                        carried = 0
                        '0'
                    }
                    else ->throw Error("aaaaa: $curV")
                }
                sb.append(v)
            }
            if(carried!=0) {
                sb.append(carried)
            }

            val out = sb.reversed().toString()
            return out
            /*
            30 to snafu 110
            70 to snafu 1=-0 -> 125 - 50 - 5 + 0 = 70

30 / 25 = 1  1xx
30 % 25 = 5
30 / 5 = 6
30 % 5 = 0


number
for i in range(10):
    pw = 5.toDouble().pow(i).toInt()
    quotient = (number / pw).toInt()
    if quotient.absoluteValue <= 2:
        break
out[i]=quotient
number = number - quotient * pw



             */
        }
    }
    fun solvePart1(): String {
        val sum = lines
            .map { convertSnafuToInteger( it )}
            .onEach{ println(it) }
            .sum()
        println("sum: $sum")
        val out = convertIntegerToSnafu(sum)
        println("out: $out")
        return out
    }

}