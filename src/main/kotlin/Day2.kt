import kotlin.math.absoluteValue

/*
https://adventofcode.com/2022/day/2
 */

class Day2 {
    /*
        A for Rock, B for Paper, and C for Scissors.

        X for Rock, Y for Paper, and Z for Scissors.

        (0 if you lost, 3 if the round was a draw, and 6 if you won)
    */
    fun convertTextToListOfWeightPairs(lines: List<String>): List<Pair<Int, Int>> =
        convertTextToListOfCharPairs(lines).map {
            Pair(mapCharToWeight[it.first]!!, mapCharToWeight[it.second]!!)
        }

    fun convertTextToListOfCharPairs(lines: List<String>): List<Pair<Char, Char>> =
        lines
            .map { it.replace(" +".toRegex(), "") }
            .map { Pair(it.first(), it.last()) }

    val mapCharToWeight = mapOf(
        'A' to 1, 'B' to 2, 'C' to 3,
        'X' to 1, 'Y' to 2, 'Z' to 3,
    )

    fun getScore(pair: Pair<Int, Int>): Int {
        val diff = pair.first - pair.second
        if (diff.absoluteValue == 0) {
            return 3
        }
        if (diff.absoluteValue == 1) {
            return if (diff < 0) 6 else 0
        }
        if (diff.absoluteValue == 2) {
            return if (diff < 0) 0 else 6
        }
        throw Error("unknown state")
    }

    fun partOneSolution(lines: List<String>): Int {
        val pairs = convertTextToListOfWeightPairs(lines)
        val roundsResults = pairs.map { it.second + getScore(it) }
        return roundsResults.sum()
    }

    fun getSpecificShapeForInstruction(pair: Pair<Int, Int>): Int {
        return when (pair.second) {
            // loose
            1 -> if (pair.first == 1) 3 else pair.first - 1
            // draw
            2 -> pair.first
            3 -> if (pair.first == 3) 1 else pair.first + 1
            else -> throw Error("Unknown case")
        }
    }

    fun partTwoSolution(lines: List<String>): Int {
        val pairs = convertTextToListOfWeightPairs(lines)
        val roundsResults = pairs.map {
            Pair(it.first, getSpecificShapeForInstruction(it))
        }
            .map {
                it.second + getScore(it)
            }
        return roundsResults.sum()
    }


}