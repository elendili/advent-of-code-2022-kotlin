import java.lang.Integer.min

class Day13 {

    fun pairIsInRightOrder(pair: Pair<List<*>, List<*>>): Boolean? {
        return pairIsInRightOrder(pair.first, pair.second)
    }

    private fun pairIsInRightOrder(first: Int, second: Int): Boolean? {
        return when {
            first < second -> true
            first > second -> false
            else -> null
        }
    }

    private fun pairIsInRightOrder(first: List<*>, second: List<*>): Boolean? {
        val min = min(first.lastIndex, second.lastIndex)
        for (i in 0..min) {
            var fv = first[i]
            var sv = second[i]
            // prepare lists if required
            if (fv is List<*> && sv is Int) {
                sv = listOf(sv)
            } else if (sv is List<*> && fv is Int) {
                fv = listOf(fv)
            }
            if (fv is List<*> && sv is List<*>) {
                val v = pairIsInRightOrder(fv, sv)
                if (v != null) {
                    return v
                }
            } else {
                val v = pairIsInRightOrder(fv as Int, sv as Int)
                if (v != null) {
                    return v
                }
            }
        }
        return when {
            first.size < second.size -> true
            first.size > second.size -> false
            else -> null
        }
    }

    fun linesToPairsList(lines: List<String>): List<Pair<List<Any>, List<Any>>> {
        val out = mutableListOf<Pair<List<Any>, List<Any>>>()
        var first: List<Any>? = listOf()
        var second: List<Any> = listOf()
        lines.forEachIndexed { i, line ->
            when {
                i % 3 == 0 -> {
                    first = Parser().stringToList(line)
                }

                i % 3 == 1 -> {
                    second = Parser().stringToList(line)
                }

                i % 3 == 2 -> {
                    out.add(Pair(first!!, second))
                    first=null
                }
            }
        }
        if(first!=null){
            out.add(Pair(first!!, second))
        }
        return out;
    }

    class Parser {
        var i = 0
        fun stringToList(line: String): List<Any> {
            val out = mutableListOf<Any>()
            var strBuf = ""
            while (i < line.length) {
                val c = line[i++]
                if (!c.isDigit()) {
                    if (strBuf.isNotEmpty()) {
                        out.add(strBuf.toInt())
                        strBuf = ""
                    }
                    when (c) {
                        ',' -> {}
                        '[' -> {
                            out.add(stringToList(line))
                        }

                        ']' -> {
                            break
                        }

                        else -> {
                            strBuf += c
                        }
                    }
                } else {
                    strBuf += c
                }
            }
            if (strBuf.isNotEmpty()) out.add(strBuf.toInt())
            return out
        }
    }


    fun partOneSolution(lines: List<String>): Int {
        val data = linesToPairsList(lines)
        val out = mutableListOf<Int>()
        for(i in data.indices){
            if( pairIsInRightOrder(data[i])!! ){
                out.add(i+1)
            }
        }
        return out.sum()
    }

    fun partTwoSolution(lines: List<String>): Int {
        val data = linesToPairsList(lines).flatMap { it.toList() }.toMutableList()
        val dividerPackets = setOf("[[2]]","[[6]]")
            .map{ Parser().stringToList(it) }
        dividerPackets.forEach{ data.add(it)}

        val sorted = data
            .sortedWith{ a,b-> if (pairIsInRightOrder(a,b)!!) 1 else -1 }
            .reversed()
        whenDebug { sorted.forEach{ println(it) } }
        val indexesOfDividers = sorted.mapIndexedNotNull { i,e->
            if(e in dividerPackets)  i+1 else null
        }
        return indexesOfDividers.reduce{a,b->a*b}
    }

}