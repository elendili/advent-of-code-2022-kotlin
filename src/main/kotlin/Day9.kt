import kotlin.math.absoluteValue
import kotlin.math.sign

class Day9 {
    /*
    Triple
    1 horizontal shift by x
    2 vertical shift by y
    3 repetitions
     */
    fun instructionToDelta(line:String):Triple<Int,Int,Int>{
        val dir = line.substringBefore(" ")
        val count = line.substringAfter(" ").toInt()
        val out = when(dir){
            "R"->Triple(1,0,count)
            "L"->Triple(-1,0,count)
            "U"->Triple(0,-1,count)
            "D"->Triple(0,1,count)
            else -> {throw Error("oh no")}
        }
        return out
    }

    fun applyContraction(head:Pair<Int,Int>, tail:Pair<Int,Int>):Pair<Int,Int>{
        val diffX = head.first - tail.first
        val diffY = head.second - tail.second
        if(diffX==0 && diffY.absoluteValue>1){
            return Pair(tail.first,tail.second+diffY.sign)
        }
        if(diffY==0 && diffX.absoluteValue>1){
            return Pair(tail.first+diffX.sign,tail.second)
        }
        if (diffX.absoluteValue+diffY.absoluteValue>2){
            return Pair(tail.first+diffX.sign,tail.second+diffY.sign)
        }
        if (diffX.absoluteValue+diffY.absoluteValue>3){
            throw Error("oh no")
        }
        return tail
    }



    fun partOneSolution(lines: List<String>): Int {
        var headPosition : Pair<Int,Int> = Pair(0,0)
        val tailLog = mutableListOf(headPosition)
        lines.map{instructionToDelta(it)}
             .forEach{ delta->
                 repeat(delta.third){
                     val newHeadPosition = Pair(headPosition.first + delta.first, headPosition.second+delta.second)
                     val newTailPosition = applyContraction(newHeadPosition, tailLog.last())
                     tailLog.add(newTailPosition)
                     headPosition=newHeadPosition

                     whenDebug {
                         val combined = tailLog+newHeadPosition
                         println(Grid(getGridPropertiesFromPairs(combined))
                             .drawPoints(combined,'#')
                             .drawPoint(newTailPosition,'T')
                             .drawPoint(newHeadPosition,'H')
                         )
                         println()
                     }
                 }
             }
        return tailLog.toSet().size
    }

    fun partTwoSolution(lines: List<String>): Int {
        val rope = MutableList(10){Pair(0,0)} // head is 0, tail is 9
        val tailPositionsSet = mutableSetOf(rope.first())
        lines.map{instructionToDelta(it)}
            .forEach{ delta->
                repeat(delta.third){

                    val head = rope.first()
                    rope[0]=Pair(head.first + delta.first, head.second+delta.second)
                    for(i in rope.indices.drop(1)) {
                        rope[i] = applyContraction(rope[i-1],rope[i])
                    }
                    tailPositionsSet.add(rope.last())

                    whenDebug {
                        println(Grid(getGridPropertiesFromPairs(rope))
                            .drawEnumeratedPoints(rope))
                        println()
                    }
                }
            }
        return tailPositionsSet.size
    }

}