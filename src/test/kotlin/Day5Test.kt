import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day5Test {
    private val input = """    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2""".lines()

    @Test
    fun getStacksTest() {
        assertEquals("[[Z, N], [M, C, D], [P]]",
            Day5().getStacks(input).toString())
    }

    @Test
    fun getMovesTest() {
        assertEquals("[Move(amount=1, from=1, to=0), Move(amount=3, from=0, to=2), Move(amount=2, from=1, to=0), Move(amount=1, from=0, to=1)]",
            Day5().getMoves(input).toString())
    }

    @Test
    fun testSolution() {
        assertEquals("CMZ",Day5().partOneSolution(input))
        assertEquals("MCD",Day5().partTwoSolution(input))
    }

    @Test
    fun solution() {
        val lines = getResourceAsLines("Day5_data.txt")
        assertEquals("VPCDMSLWJ",Day5().partOneSolution(lines))
        assertEquals("TPWCGNCCG",Day5().partTwoSolution(lines))
    }
}