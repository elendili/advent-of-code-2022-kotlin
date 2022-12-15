import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day14Test {
    private val input = """498,4 -> 498,6 -> 496,6
503,4 -> 502,4 -> 502,9 -> 494,9""".lines()


    @Test
    fun testSolution1() {
        assertEquals(24, Day14().partOneSolution(input))
    }

    @Test
    fun testSolution2() {
        assertEquals(93, Day14().partTwoSolution(input))
    }

    @Test
    fun solution1() {
        val lines = getResourceAsLines("Day14_data.txt")
        assertEquals(858, Day14().partOneSolution(lines))
    }

    @Test
    fun solution2() {
        val lines = getResourceAsLines("Day14_data.txt")
        assertEquals(26845, Day14().partTwoSolution(lines))
    }


}