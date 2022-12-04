import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day2KtTest {
    private val input = """A Y
                B X
                C Z""".lines()

    @Test
    fun testSolution() {
        assertEquals(15,Day2().partOneSolution(input))
        assertEquals(12,Day2().partTwoSolution(input))
    }


    @Test
    fun solution() {
        val lines = getResourceAsLines("Day2_data.txt")
        assertEquals(13682,Day2().partOneSolution(lines))
        assertEquals(12881,Day2().partTwoSolution(lines))
    }
}