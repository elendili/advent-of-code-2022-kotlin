import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day9Test {
    private val input1 = """R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2""".lines()
    private val input2 = """R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20""".lines()


    @Test
    fun testSolution1() {
        assertEquals(13, Day9().partOneSolution(input1))
    }

    @Test
    fun testSolution2() {
        assertEquals(1, Day9().partTwoSolution(input1))
    }

    @Test
    fun testSolution22() {
        assertEquals(36, Day9().partTwoSolution(input2))
    }

    @Test
    fun solution() {
        val lines = getResourceAsLines("Day9_data.txt")
        assertEquals(6057,Day9().partOneSolution(lines))
        assertEquals(2514,Day9().partTwoSolution(lines))
    }
}