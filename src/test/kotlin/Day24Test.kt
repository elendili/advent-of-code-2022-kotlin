import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import kotlin.test.Ignore

class Day24Test {
    private val input = """#.######
#>>.<^<#
#.<..<<#
#>v.><>#
#<^v^^>#
######.#""".lines().filter{it.isNotEmpty()}

    @Test
    fun testSolution1() {
        assertEquals(18, Day24(input).solvePart1())
    }
    @Test
    fun solution1() {
        assertEquals(245, Day24(getResourceAsLines("Day24_data.txt")).solvePart1())
    }
    @Test
    fun testSolution2() {
        assertEquals(54, Day24(input).solvePart2())
    }
    @Test
    fun solution2() {
        assertEquals(245, Day24(getResourceAsLines("Day24_data.txt")).solvePart2())
    }
}