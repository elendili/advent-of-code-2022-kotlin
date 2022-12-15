import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day15Test {
    private val input = """Sensor at x=2, y=18: closest beacon is at x=-2, y=15
Sensor at x=9, y=16: closest beacon is at x=10, y=16
Sensor at x=13, y=2: closest beacon is at x=15, y=3
Sensor at x=12, y=14: closest beacon is at x=10, y=16
Sensor at x=10, y=20: closest beacon is at x=10, y=16
Sensor at x=14, y=17: closest beacon is at x=10, y=16
Sensor at x=8, y=7: closest beacon is at x=2, y=10
Sensor at x=2, y=0: closest beacon is at x=2, y=10
Sensor at x=0, y=11: closest beacon is at x=2, y=10
Sensor at x=20, y=14: closest beacon is at x=25, y=17
Sensor at x=17, y=20: closest beacon is at x=21, y=22
Sensor at x=16, y=7: closest beacon is at x=15, y=3
Sensor at x=14, y=3: closest beacon is at x=15, y=3
Sensor at x=20, y=1: closest beacon is at x=15, y=3""".lines()


    @Test
    fun testSolution1() {
        assertEquals(26, Day15().partOneSolution(input, 10))
    }

    @Test
    fun testSolution2() {
        assertEquals(56000011, Day15().partTwoSolution(input, 20))
    }

    @Test
    fun solution1() {
        val lines = getResourceAsLines("Day15_data.txt")
        assertEquals(5181556, Day15().partOneSolution(lines, 2000000))
    }

    @Test
    fun solution2() {
        val lines = getResourceAsLines("Day15_data.txt")
        assertEquals(12817603219131, Day15().partTwoSolution(lines, 4000000))
    }
}