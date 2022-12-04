import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day3Test {
    private val input = """vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw""".lines()

    @Test
    fun charToPriorityTest() {
        assertEquals(1,Day3().charToPriority('a'))
        assertEquals(26,Day3().charToPriority('z'))
        assertEquals(27,Day3().charToPriority('A'))
        assertEquals(52,Day3().charToPriority('Z'))
    }

    @Test
    fun testSolution() {
        assertEquals(157,Day3().partOneSolution(input))
        assertEquals(70,Day3().partTwoSolution(input))
    }


    @Test
    fun solution() {
        val lines = getResourceAsLines("Day3_data.txt")
        assertEquals(7701,Day3().partOneSolution(lines))
        assertEquals(2644,Day3().partTwoSolution(lines))
    }
}