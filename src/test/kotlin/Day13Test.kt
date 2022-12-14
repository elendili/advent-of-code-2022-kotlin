import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day13Test {
    private val input = """[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]""".lines()


    @Test
    fun linesToPairsListTest() {
        val actual = Day13().linesToPairsList(input)
            .map{ it.first+"\n"+it.second +"\n"  }.joinToString("\n")
        assertEquals("", actual )
    }

    @Test
    fun testSolution1_pair1() {
        assertEquals(1, Day13().partOneSolution(input.take(2)) )
        assertEquals(3, Day13().partOneSolution(input.take(6)) )
        assertEquals(7, Day13().partOneSolution(input.take(12)) )// 4 pairs
        assertEquals(13, Day13().partOneSolution(input) )
    }

    @Test
    fun testSolution1() {
        assertEquals(13, Day13().partOneSolution(input))
    }

    @Test
    fun testSolution2() {
        assertEquals(140, Day13().partTwoSolution(input))
    }

    @Test
    fun solution1() {
        val lines = getResourceAsLines("Day13_data.txt")
        assertEquals(5843, Day13().partOneSolution(lines))
    }

    @Test
    fun solution2() {
        val lines = getResourceAsLines("Day13_data.txt")
        assertEquals(26289, Day13().partTwoSolution(lines))
    }

}