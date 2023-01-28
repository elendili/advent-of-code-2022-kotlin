import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day22Test {
    private val input = """
        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R5L5R10L4R5L5""".lines().filter{it.isNotEmpty()}


    @Test
    fun instrTest() {
        assertEquals("[10, R, 5, L, 5, R, 10, L, 4, R, 5, L, 5]",
            Day22().lineToInstructions("10R5L5R10L4R5L5").toString())
    }


    @Test
    fun testSolution1() {
        assertEquals(6032, Day22().partOneSolution(input))
    }

    @Test
    fun solution1() {
        val lines = getResourceAsLines("Day22_data.txt")
        assertEquals(93226, Day22().partOneSolution(lines))
    }


    @Test
    fun testSolution2() {
        assertEquals(5031, Day22().partTwoSolution(input))
    }

    @Test
    fun solution2() {
        val lines = getResourceAsLines("Day22_data.txt")
        assertEquals(93226, Day22().partTwoSolution(lines))
    }

}