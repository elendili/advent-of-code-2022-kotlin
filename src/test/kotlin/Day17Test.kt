import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day17Test {
    private val input = """>>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"""


    @Test
    fun testSolution1() {
        assertEquals(4, Day17().partOneSolution(input,2))
        assertEquals(7, Day17().partOneSolution(input,4))
        assertEquals(9, Day17().partOneSolution(input,5))
        assertEquals(3068, Day17().partOneSolution(input))
    }

    @Test
    fun solution1() {
        val lines = getResourceAsLines("Day17_data.txt")
        assertEquals(3209, Day17().partOneSolution(lines[0]))
    }

    @Test
    fun testSolution2() {
        assertEquals(1514285714288, Day17().partTwoSolution(input))
    }

    @Test
    fun solution2() {
        val lines = getResourceAsLines("Day17_data.txt")
        assertEquals(1580758017509, Day17().partTwoSolution(lines[0]))
    }
}