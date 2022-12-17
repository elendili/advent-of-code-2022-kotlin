import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day16Test {
    private val input = """Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
Valve BB has flow rate=13; tunnels lead to valves CC, AA
Valve CC has flow rate=2; tunnels lead to valves DD, BB
Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
Valve EE has flow rate=3; tunnels lead to valves FF, DD
Valve FF has flow rate=0; tunnels lead to valves EE, GG
Valve GG has flow rate=0; tunnels lead to valves FF, HH
Valve HH has flow rate=22; tunnel leads to valve GG
Valve II has flow rate=0; tunnels lead to valves AA, JJ
Valve JJ has flow rate=21; tunnel leads to valve II""".lines()


    @Test
    fun getMaxPossibleReleaseForMinutesCustomDataTest1() {
        assertEquals(
            20, Day16.Valves("Valve AA has flow rate=10; tunnels lead to valves ".lines())
                .getMaxPossibleReleaseForMinutesRecursive(3)
        )
    }

    @Test
    fun getMaxPossibleReleaseForMinutesCustomDataTest2() {
        assertEquals(43, Day16.Valves("""Valve AA has flow rate=10; tunnels lead to valves BB
            |Valve BB has flow rate=13; tunnels lead to valves AA
        """.trimMargin().lines())
//                o AA, >BB, oBB
//                  0,  +10, +10, +23 = 43
//                >BB,  oBB, >AA, oAA
//                  0,  0,   +13, +13 = 26
            .getMaxPossibleReleaseForMinutesRecursive(4))
    }

    @Test
    fun getMaxPossibleReleaseForMinutesTest_5() {
        assertEquals(63, Day16.Valves(input).getMaxPossibleReleaseForMinutesRecursive(5))
    }

    @Test
    fun getMaxPossibleReleaseForMinutesTest() {
        assertEquals(93, Day16.Valves(input).getMaxPossibleReleaseForMinutesRecursive(6))
    }

    @Test
    fun testSolution1() {
        assertEquals(1651, Day16().partOneSolution(input))
    }

    @Test
    fun testSolution2() {
        assertEquals(1707, Day16().partTwoSolution(input))
    }

    @Test
    fun solution1() {
        val lines = getResourceAsLines("Day16_data.txt")
        assertEquals(1789, Day16().partOneSolution(lines))
    }

    @Test
    fun solution2() {
        val lines = getResourceAsLines("Day16_data.txt")
        assertEquals(2496, Day16().partTwoSolution(lines))
    }


}