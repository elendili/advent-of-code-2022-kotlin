import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day4Test {
    private val input = """2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8""".lines()

    @Test
    fun testSolution() {
        assertEquals(2,Day4().partOneSolution(input))
        assertEquals(4,Day4().partTwoSolution(input))
    }

    @Test
    fun solution() {
        val lines = getResourceAsLines("Day4_data.txt")
        assertEquals(605,Day4().partOneSolution(lines))
        assertEquals(914,Day4().partTwoSolution(lines))
    }
}