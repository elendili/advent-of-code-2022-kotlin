/*
https://adventofcode.com/2022/day/3
 */

class Day6 {

    fun searchSubstringWithUniqueCharacters(string:String, windowSize:Int):Int{
        val firstCharIndex = string.windowedSequence(windowSize).indexOfFirst { hasUniqueSymbols(it)}
        return firstCharIndex+windowSize
    }

    fun hasUniqueSymbols(string:String):Boolean{
        return string.toSet().size == string.length
    }

    fun partOneSolution(line: String): Int {
        return searchSubstringWithUniqueCharacters(line, 4)
    }

    fun partTwoSolution(line: String): Int {
        return searchSubstringWithUniqueCharacters(line, 14)
    }

}