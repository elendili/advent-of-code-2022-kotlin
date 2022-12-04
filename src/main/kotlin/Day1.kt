/*
https://adventofcode.com/2022/day/1
 */


fun getResourceAsLines(path: String): List<String> =
    object {}.javaClass.getResource(path).readText().lines()

fun taskASolution(lines: List<String>): Int {
    return getCaloriesByElf(lines).max()
}

fun taskBSolution(lines: List<String>): Int {
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

fun main(args: Array<String>) {
    val lines = getResourceAsLines("Day1_data.txt")
    println("task a: " + taskASolution(lines))
    println("task b: " + taskBSolution(lines))
}
