import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day8Test {
    private val input = """30373
25512
65332
33549
35390""".lines()


    @Test
    fun testGetScenicScoreForTree() {
        assertEquals(4, Day8().getScenicScoreForTree(linesToMatrix(input),1,2))
        assertEquals(8, Day8().getScenicScoreForTree(linesToMatrix(input),3,2))
    }

    @Test
    fun testSolution() {
        assertEquals(21, Day8().partOneSolution(input))
        assertEquals(8, Day8().partTwoSolution(input))
    }

    @Test
    fun solution() {
        val lines = getResourceAsLines("Day8_data.txt")
        assertEquals(1823,Day8().partOneSolution(lines))
        assertEquals(211680,Day8().partTwoSolution(lines))
    }
}