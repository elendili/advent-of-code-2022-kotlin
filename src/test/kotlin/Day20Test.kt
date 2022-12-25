import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day20Test {
    private val input = """1
2
-3
3
-2
0
4""".lines()

    @Test
    fun testMove() {
        val _1= Day20.Node(1)
        val _2= Day20.Node(2)
        val _3= Day20.Node(3)
        Day20().connectNodesInCircle(listOf(_1,_2,_3))
        assertEquals("[1, 2, 3]",_1.getFullListFromCurrentForward().toString())
        assertEquals("[2, 3, 1]",_2.getFullListFromCurrentForward().toString())
        assertEquals("[3, 1, 2]",_3.getFullListFromCurrentForward().toString())
        _3.move(-1)
        assertEquals("[1, 3, 2]",_1.getFullListFromCurrentForward().toString())
        assertEquals("[2, 1, 3]",_2.getFullListFromCurrentForward().toString())
        assertEquals("[3, 2, 1]",_3.getFullListFromCurrentForward().toString())
        assertEquals("[1, 2, 3]",_1.getFullListFromCurrentBackward().toString())
        assertEquals("[2, 3, 1]",_2.getFullListFromCurrentBackward().toString())
        assertEquals("[3, 1, 2]",_3.getFullListFromCurrentBackward().toString())
        _3.move(1)
        assertEquals("[1, 2, 3]",_1.getFullListFromCurrentForward().toString())
        _3.move(-3)
        assertEquals("[1, 3, 2]",_1.getFullListFromCurrentForward().toString())
        assertEquals("[1, 2, 3]",_1.getFullListFromCurrentBackward().toString())
    }

    @Test
    fun testSolution1() {
        assertEquals(3, Day20().partOneSolution(input))
    }

    @Test
    fun testSolution2() {
        assertEquals(1623178306, Day20().partTwoSolution(input))
    }

    @Test
    fun solution1() {
        val lines = getResourceAsLines("Day20_data.txt")
        assertEquals(5498, Day20().partOneSolution(lines))
    }

    @Test
    fun solution2() {
        val lines = getResourceAsLines("Day20_data.txt")
        assertEquals(3390007892081, Day20().partTwoSolution(lines))
    }

}