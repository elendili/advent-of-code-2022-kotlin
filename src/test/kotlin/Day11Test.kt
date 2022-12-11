import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day11Test {
    private val input = """Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1""".lines()


    @Test
    fun testSolution1() {
        assertEquals(10605, Day11().partOneSolution(input))
    }

    @Test
    fun testSolution2() {
        assertEquals(2713310158, Day11().partTwoSolution(input))
    }

    @Test
    fun solution1() {
        val lines = getResourceAsLines("Day11_data.txt")
        assertEquals(90882, Day11().partOneSolution(lines))
    }

    @Test
    fun solution2() {
        val lines = getResourceAsLines("Day11_data.txt")
        assertEquals(30893109657, Day11().partTwoSolution(lines))
    }

}