class Day4 {
    fun rangesToIntPairs(lines: List<String>) : List<Pair<Pair<Int, Int>, Pair<Int, Int>>> =lines.map { it.split(",") }
            .map {
                val lineOfPairs = it.map { it2 ->
                    val range: List<Int> = it2.split("-").mapToInts()
                    Pair(range.first(), range.last())
                }
                Pair(lineOfPairs.first(), lineOfPairs.last())
            }

    fun partOneSolution(lines: List<String>): Int {
        val l = rangesToIntPairs(lines)
        return l.filter { rangeIsInsideOfOther(it.first,it.second) }.size
    }

    fun rangeIsInsideOfOther(r1: Pair<Int, Int>, r2: Pair<Int, Int>):Boolean {
        return firstRangeIsInsideOfSecond(r1,r2) || firstRangeIsInsideOfSecond(r2,r1)
    }

    fun firstRangeIsInsideOfSecond(r1: Pair<Int, Int>, r2: Pair<Int, Int>):Boolean {
        return r1.first>=r2.first && r1.second<=r2.second
    }

    fun rangesOverlap(r1: Pair<Int, Int>, r2: Pair<Int, Int>):Boolean {
        return rangesOverlap1(r1,r2) || rangesOverlap1(r2,r1)
    }

    fun rangesOverlap1(r1: Pair<Int, Int>, r2: Pair<Int, Int>):Boolean {
        return r1.first>=r2.first && r1.first<=r2.second  ||
        r1.second<=r2.second && r1.second>=r2.first

        /*
5-7,7-9 overlaps in a single section, 7.
2-8,3-7 overlaps all of the sections 3 through 7.
6-6,4-6 overlaps in a single section, 6.
2-6,4-8 overlaps in sections 4, 5, and 6.
         */
    }

    fun partTwoSolution(lines: List<String>): Int {
        val l = rangesToIntPairs(lines)
        return l.filter { rangesOverlap(it.first,it.second) }.size
    }


}