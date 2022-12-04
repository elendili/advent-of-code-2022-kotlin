/*
https://adventofcode.com/2022/day/1
 */


fun getResourceAsLines(path: String): List<String> =
    object {}.javaClass.getResource(path).readText().lines()


class Day1 {

    fun partOneSolution(lines: List<String>): Int {
        return getCaloriesByElf(lines).max()
    }

    fun partTwoSolution(lines: List<String>): Int {
        return getCaloriesByElf(lines).sortedDescending().subList(0,3).sum()
    }

    fun getCaloriesByElf(lines: List<String>): List<Int> {
        val caloriesByElf = mutableListOf(0)
        for (item in lines) {
            if (item == "") {
                caloriesByElf.add(0)
            } else {
                caloriesByElf[caloriesByElf.lastIndex] = caloriesByElf.last() + item.toInt()
            }
        }
        return caloriesByElf
    }

}
