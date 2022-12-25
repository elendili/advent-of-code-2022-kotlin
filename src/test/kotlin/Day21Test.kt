import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day21Test {
    private val input = """root: pppw + sjmn
dbpl: 5
cczh: sllz + lgvd
zczc: 2
ptdq: humn - dvpt
dvpt: 3
lfqf: 4
humn: 5
ljgn: 2
sjmn: drzm * dbpl
sllz: 4
pppw: cczh / lfqf
lgvd: ljgn * ptdq
drzm: hmdt - zczc
hmdt: 32""".lines()


    @Test
    fun testSolution1() {
        assertEquals(152, Day21().partOneSolution(input))
    }

    @Test
    fun testSolution2() {
        assertEquals(301, Day21().partTwoSolution(input))
    }

    @Test
    fun solution1() {
        val lines = getResourceAsLines("Day21_data.txt")
        assertEquals(83056452926300, Day21().partOneSolution(lines))
    }

    @Test
    fun solution2() {
        val lines = getResourceAsLines("Day21_data.txt")
        assertEquals(3469704905529, Day21().partTwoSolution(lines))
    }

}