import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day6Test {
    private val input = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"

    @Test
    fun testSolution() {
        assertEquals(7, Day6().partOneSolution("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(5, Day6().partOneSolution("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals(6, Day6().partOneSolution("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals(10, Day6().partOneSolution("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals(11, Day6().partOneSolution("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))

        assertEquals(19, Day6().partTwoSolution("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(23, Day6().partTwoSolution("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals(23, Day6().partTwoSolution("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals(29, Day6().partTwoSolution("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals(26, Day6().partTwoSolution("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
    }

    @Test
    fun solution() {
        val lines = getResourceAsText("Day6_data.txt")
        assertEquals(1582,Day6().partOneSolution(lines))
        assertEquals(3588,Day6().partTwoSolution(lines))
    }
}