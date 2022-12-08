import kotlin.math.absoluteValue
import kotlin.math.max

class Day8 {

    fun visibilityFromOutsideMatrix(matrix:List<List<Int>>) : List<List<Int>> {
        val visualMatrix = MutableList(matrix.size) { MutableList(matrix[0].size) {0 }}
        // from the left side
        markVisibility(matrix, visualMatrix, matrix.indices, matrix[0].indices, true)
        // from the right side
        markVisibility(matrix, visualMatrix, matrix.indices, matrix[0].indices.reversed(), true)
        // from the top
        markVisibility(matrix, visualMatrix, matrix[0].indices, matrix.indices, false)
        // from the bottom
        markVisibility(matrix, visualMatrix, matrix[0].indices, matrix.indices.reversed(), false)
        return visualMatrix
    }

    private fun markVisibility(
        matrix: List<List<Int>>,
        visualMatrix: MutableList<MutableList<Int>>,
        outerIndices: IntProgression,
        innerIndices: IntProgression,
        flag:Boolean
    ) {
        for (i in outerIndices) {
            var localMax = -1
            for (j in innerIndices) {
                val v = if (flag) matrix[i][j] else matrix[j][i]
                if (v > localMax) {
                    if (flag) visualMatrix[i][j] = 1 else visualMatrix[j][i] = 1
                    localMax = v
                }
            }
        }
    }

    fun partOneSolution(lines: List<String>): Int {
        val matrix = linesToMatrix(lines)
        val vm = visibilityFromOutsideMatrix(matrix)
        println( array2dToString(matrix))
        println()
        println( array2dToString(vm))
        return vm.sumOf { it.sum() }
    }

    fun getScenicScoreForTree(matrix: List<List<Int>>, row:Int, column:Int):Int{
        val heigth = matrix[row][column]
        var out = 1
        // toEast
        for (i in (column+1)..matrix.lastIndex){
            val ch = matrix[row][i]
            if(ch>=heigth || i==matrix.lastIndex){
                out *= i.minus(column).absoluteValue
                break
            }
        }
        // toWest
        for (i in (column-1)downTo 0){
            val ch = matrix[row][i]
            if(ch>=heigth || i==0){
                out *= i.minus(column).absoluteValue
                break
            }
        }
        // toSouth
        for (i in (row+1).. matrix.lastIndex){
            val ch = matrix[i][column]
            if(ch>=heigth || i==matrix.lastIndex){
                out *= i.minus(row).absoluteValue
                break
            }
        }
        // toNorth
        for (i in (row-1)downTo 0){
            val ch = matrix[i][column]
            if(ch>=heigth || i==0){
                out *= i.minus(row).absoluteValue
                break
            }
        }
        return out
    }

    fun partTwoSolution(lines: List<String>): Int {
        val matrix = linesToMatrix(lines)
        val vm = visibilityFromOutsideMatrix(matrix)
        var maxScore = 0
        for(row in 1 until matrix.lastIndex){
            for(column in 1 until matrix.lastIndex) {
                if(vm[row][column]>0){
                    maxScore = max(maxScore, getScenicScoreForTree(matrix, row, column))
                }
            }
        }
        return maxScore
    }

}