import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day1KtTest {
    val input = """1000
        2000
        3000
        
        4000
        
        5000
        6000
        
        7000
        8000
        9000
        
        10000""".replace(" +".toRegex(),"").lines()

    @Test
    fun getDay1aSolution() {
        assertEquals(24000,taskASolution(input))
    }

    @Test
    fun getDay1bSolution() {
        assertEquals(45000,taskBSolution(input))
    }
}