import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day12Test {
    private val input = """Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi""".lines()


    @Test
    fun testSolution1() {
        assertEquals(31, Day12().partOneSolution(input))
    }

    @Test
    fun testSolution2() {
        assertEquals(29, Day12().partTwoSolution(input))
    }

    @Test
    fun solution1() {
        val lines = getResourceAsLines("Day12_data.txt")
        assertEquals(437, Day12().partOneSolution(lines))
    }

    @Test
    fun solution2() {
        val lines = getResourceAsLines("Day12_data.txt")
        assertEquals(430, Day12().partTwoSolution(lines))
    }

}