import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day1Test {
    val input = """1000
        2000
        3000
        
        4000
        
        5000
        6000
        
        7000
        8000
        9000
        
        10000"""
        .replace(" +".toRegex(),"").lines()

    @Test
    fun partOneSolutionTest() {
        assertEquals(24000,Day1().partOneSolution(input))
    }

    @Test
    fun partTwoSolutionTest() {
        assertEquals(45000,Day1().partTwoSolution(input))
    }

    @Test
    fun solution() {
        val lines = getResourceAsLines("Day1_data.txt")
        assertEquals(68442,Day1().partOneSolution(lines))
        assertEquals(204837,Day1().partTwoSolution(lines))
    }
}