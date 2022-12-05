/*
https://adventofcode.com/2022/day/3
 */

fun CharSequence.splitIgnoreEmpty(regex : Regex): List<String> {
    return this.split(regex).filter {
        it.isNotEmpty()
    }
}

fun Iterable<String>.mapToInts(): List<Int> {
    return this.map{it.toInt()}
}


class Day5 {
    data class Move(val amount:Int, val from:Int, val to:Int)
    fun getStacks(list: List<String>): MutableList<MutableList<Char>> {
        val stacksLines = list.takeWhile { it.contains("[") }
        val indexLine = list.dropWhile { it.contains("[") }.first()
        val stacksCount = indexLine.splitIgnoreEmpty(" +".toRegex()).last().toInt()
        val stacks = MutableList(stacksCount) {
            mutableListOf<Char>()
        }
        indexLine.forEachIndexed { i, c ->
            if (c.isDigit()) {
                val stack = stacks[c.digitToInt() - 1]
                stacksLines.forEach {
                    if (it[i].isUpperCase()) {
                        stack.add(0,it[i])
                    }
                }
            }
        }
        return stacks
    }

    fun getMoves(list: List<String>): List<Move> {
        val moveLines = list.filter { it.startsWith("move") }
            .map{it.splitIgnoreEmpty("\\D+".toRegex()).mapToInts()}
            .map{Move(it.first(),it[1]-1,it.last()-1)}
        return moveLines
    }

    private fun executeOneByOneMovementOnStacks(stacks: List<MutableList<Char>>, move: Move){
        repeat(move.amount){
            val c = stacks[move.from].removeLast()
            stacks[move.to].add(c)
        }
    }

    private fun executeWholeMovementOnStacks(stacks: MutableList<MutableList<Char>>, move: Move){
        val amount = move.amount
        val fromList = stacks[move.from]
        val toList = stacks[move.to]
        toList.addAll( fromList.subList(fromList.size-amount, fromList.size) )
        stacks[move.from] = fromList.subList(0,fromList.size-amount)
    }

    fun partOneSolution(lines: List<String>): String {
        val stacks = getStacks(lines)
        val moves = getMoves(lines)
        moves.forEach{executeOneByOneMovementOnStacks(stacks, it)}
        return stacks.map { it.last() }.joinToString("")
    }


    fun partTwoSolution(lines: List<String>): String {
        val stacks = getStacks(lines)
        val moves = getMoves(lines)
        moves.forEach{executeWholeMovementOnStacks(stacks, it)}
        return stacks.map { it.last() }.joinToString("")
    }


}