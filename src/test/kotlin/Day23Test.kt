import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import kotlin.test.Ignore

class Day23Test {
    private val input = """....#..
..###.#
#...#.#
.#...##
#.###..
##.#.##
.#..#..""".lines().filter{it.isNotEmpty()}

    @Test
    fun testSolution1() {
        assertEquals(110, Day23(input).solvePart1())
    }

    @Test
    fun solution1() {
        assertEquals(4000, Day23(getResourceAsLines("Day23_data.txt")).solvePart1())
    }

    @Test
    fun testSolution2() {
        assertEquals(20, Day23(input).solvePart2())
    }

    @Test
    fun solution2() {
        assertEquals(1040, Day23(getResourceAsLines("Day23_data.txt")).solvePart2())
    }

}