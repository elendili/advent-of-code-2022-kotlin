import java.util.LinkedList

class Day12 {
    fun getPointFrom(lines: List<String>, c: Char): PointYX? {
        for (y in lines.indices) {
            for (x in lines[y].indices) {
                if (lines[y][x] == c) {
                    return PointYX(y, x)
                }
            }
        }
        return null;
    }

    fun getPointsFrom(lines: List<String>, c: Char): MutableList<PointYX> {
        val out = mutableListOf<PointYX>()
        for (y in lines.indices) {
            for (x in lines[y].indices) {
                if (lines[y][x] == c) {
                    out.add(PointYX(y, x))
                }
            }
        }
        return out;
    }

    /**
    v..v<<<<
    >v.vv<<^
    .>vv>E^^
    ..v>>>^^
    ..>>>>>^
     */

    fun drawPathOnGrid(lines:List<String>, path:List<PointYX>):String {
        val plane:Array<Array<Char>> = Array(lines.size) { Array(lines[0].length) { '.' } }
        for(i in 0 until path.lastIndex){
            val v = path[i]
            val next = path[i+1]
            val delta = next-v
            val newValue:Char = when(next-v){
                PointYX(-1,0)->{ '^'  }
                PointYX(1,0) ->{ 'v'  }
                PointYX(0,-1)->{ '<'  }
                PointYX(0,1) ->{ '>'  }
                else -> error("delta is unknown: $delta")
            }
            plane[v.y][v.x]=newValue
        }
        path.last().let {
            plane[it.y][it.x]='E'
        }
        path.first().let {
            plane[it.y][it.x]='S'
        }
        return array2dToString(plane)
    }

    fun searchShortPathLength(lines: List<String>, from:PointYX, to:PointYX):Int?{
        val height = lines.size
        val width = lines[0].length
        val q = LinkedList<PointYX>()
        q.add(from)
        val addedToQ = mutableSetOf<PointYX>(from)
        var level = 0
        while(!q.isEmpty()){
            // inner loop to calc levels
            var levelSize = q.size
            whenDebug {
                println("${level}. ${levelSize}. visitedSize=${addedToQ.size}")
            }
            while(levelSize-- != 0){
                val v = q.pollFirst()
                if(v == to){
                    whenDebug {
                        println(drawPathOnGrid(lines,v.getPathToCurrent()))
                    }
                    return level
                }
                var currentPointHeight = lines[v.y][v.x]
                currentPointHeight = if (currentPointHeight=='S') 'a' else currentPointHeight
                listOf(v.deltaX(1),v.deltaX(-1),v.deltaY(1),v.deltaY(-1))
                    .filter{ !addedToQ.contains(it) }
                    .filter { it.x in 0 until width && it.y in 0 until height }
                    .filter{
                        var c=lines[it.y][it.x]
                        c = if(c=='E') 'z' else c
                        c<=currentPointHeight+1
                    }
                    .forEach{
                        q.addLast(it)
                        addedToQ.add(it)
                    }
            }
            level++;
        }
        return null
    }

    fun partOneSolution(lines: List<String>): Int {
        val start = getPointFrom(lines, 'S')!!
        val end = getPointFrom(lines, 'E')!!
        return searchShortPathLength(lines,start,end)!!
    }

    fun partTwoSolution(lines: List<String>): Int {
        val start = getPointFrom(lines, 'S')!!
        val end = getPointFrom(lines, 'E')!!
        val aList = getPointsFrom(lines,'a')
        aList.add(start)
        val out = aList.map { searchShortPathLength(lines, it, end) }.filterNotNull().minOf { it }
        return out
    }

}