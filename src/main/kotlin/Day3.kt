
class Day3 {
    fun charToPriority(c:Char):Int{
        return if(c.isLowerCase()) (c-'a')+1 else (c-'A')+27
    }

    fun findCommonSymbolsInStrings(vararg strings:String):Set<Char> {
        return findCommonSymbolsInStrings(strings.toList())
    }

    fun findCommonSymbolsInStrings(strings:List<String>):Set<Char> {
        val out = strings.map { it.toCharArray().toSet() }
            .reduce{ it1,it2 ->it1.intersect(it2) }
        return out
    }

    fun partOneSolution(lines: List<String>): Int {
        val out = lines.map {
            findCommonSymbolsInStrings(it.substring(0, it.length / 2), it.substring(it.length / 2))
        }
            .flatten().sumOf { charToPriority(it) }
        return out
    }

    fun partTwoSolution(lines: List<String>): Int {
        val out = lines.chunked(3)
            .flatMap {
                findCommonSymbolsInStrings(it)
            }.sumOf { charToPriority(it) }
        return out
    }



}