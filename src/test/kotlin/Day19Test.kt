import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day19Test {
    private val input = """Blueprint 1:
  Each ore robot costs 4 ore.
  Each clay robot costs 2 ore.
  Each obsidian robot costs 3 ore and 14 clay.
  Each geode robot costs 2 ore and 7 obsidian.
Blueprint 2:
  Each ore robot costs 2 ore.
  Each clay robot costs 3 ore.
  Each obsidian robot costs 3 ore and 8 clay.
  Each geode robot costs 3 ore and 12 obsidian.""".lines()


    @Test
    fun testSolution1_1() {
        assertEquals(9, Day19().partOneSolution(input.take(5)))
    }

    @Test
    fun testSolution1_2() {
        assertEquals(12, Day19().partOneSolution(input.drop(5)))
    }

    @Test
    fun testSolution1_all() {
        assertEquals(33, Day19().partOneSolution(input))
    }

    @Test
    fun testSolution2_1() {
        assertEquals(56, Day19().partTwoSolution(input.take(5)))
    }

    @Test
    fun testSolution2_2() {
        assertEquals(62, Day19().partTwoSolution(input.drop(5)))
    }

    @Test
    fun testSolution2_all() {
        assertEquals(56*62, Day19().partTwoSolution(input))
    }

    @Test
    fun solution1() {
        val lines = getResourceAsLines("Day19_data.txt")
        assertEquals(1528, Day19().partOneSolution(lines))
    }
}