import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day25Test {
    private val input = """
1=-0-2
12111
2=0=
21
2=01
111
20012
112
1=-1=
1-12
12
1=
122""".lines().filter{it.isNotEmpty()}


    @ParameterizedTest
    @CsvSource(
//        "-2, =",
//        "-1, -",

        "0, 0",
        "1, 1",
        "2, 2",
        "3, 1=",
        "4, 1-",
        "5, 10",
        "6, 11",
        "7, 12",
        "8, 2=",
        "9, 2-",
        "10,20",
        "11,21",
        "12,22",
          "13,1==",
          "14,1=-",
          "15,1=0",
          "16,1=1",
          "17,1=2",

        "30, 110",
        "70, 1=-0",
        "976, 2=-01",
        "966, 2=-=1",
        "3125, 100000",
        "3126, 100001",
        "4890, 2=-1=0",
        "470, 1---0",
        "24, 10-",
        "120, 10-0",
        "495, 1-0-0",
        "2470, 1-0--0",
        "12345, 1-0---0",
        "314159265, 1121-1110-1=0"
    )
    fun convertSNAFUToIntegerTest(integer:Long, snafu:String) {
        body(integer, snafu)
    }

    fun body(integer:Long, snafu:String) {
        assertEquals(integer, Day25.convertSnafuToInteger(snafu))
        assertEquals(snafu, Day25.convertIntegerToSnafu(integer))
    }

    @Test
    fun testSolution1() {
        assertEquals("2=-1=0", Day25(input).solvePart1())
    }

    @Test
    fun solution1() {
        assertEquals("2==0=0===02--210---1", Day25(getResourceAsLines("Day25_data.txt")).solvePart1())
    }
}