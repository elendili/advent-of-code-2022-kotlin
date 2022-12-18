import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day18Test {
    private val input = """2,2,2
1,2,2
3,2,2
2,1,2
2,3,2
2,2,1
2,2,3
2,2,4
2,2,6
1,2,5
3,2,5
2,1,5
2,3,5""".lines()


    @Test
    fun testSolution1() {
        assertEquals(10, Day18().partOneSolution("1,1,1\n2,1,1".lines()))
        assertEquals(64, Day18().partOneSolution(input))
    }

    @Test
    fun testSolution2() {
        assertEquals(58, Day18().partTwoSolution(input))
    }

    @Test
    fun solution1() {
        val lines = getResourceAsLines("Day18_data.txt")
        assertEquals(4302, Day18().partOneSolution(lines))
    }

    @Test
    fun solution2() {
        val lines = getResourceAsLines("Day18_data.txt")
        assertEquals(2492, Day18().partTwoSolution(lines))
    }
}