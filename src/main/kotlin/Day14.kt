class Day14 {

    fun linesToPaths(lines: List<String>): List<List<Pair<Int, Int>>> {
        return lines.map {
            it.split(" -> ")
                .map{ p->
                    Pair(p.substringBefore(",").toInt(),
                        p.substringAfter(",").toInt())
                }
        }
    }

    fun simulateDropOnGrid(grid:Grid, origin:PointYX): Boolean {
        var p:PointYX = origin
        val deltas = listOf(PointYX(1,0),PointYX(1,-1),PointYX(1,1))
        while(true){
            val pLocal = deltas
                .map{ p.applyDelta(it) }
                .firstOrNull { grid.isOnGrid(it) && grid.getPoint(it)=='.' }
            when{
                pLocal==null->{
                    grid.drawPoint(p, 'o')
                    return !grid.isOnEdge(p)
                }
                !grid.isOnGrid(pLocal) -> {
                    return false
                }
            }
            p = pLocal!!
        }
    }

    fun partOneSolution(lines: List<String>): Int {
        val paths = linesToPaths(lines)
        val gp = getGridPropertiesFromPoints(paths.flatten()+Pair(500,0))
        val grid = Grid(gp)
        paths.forEach{grid.drawLines(it,'#')}
        //
        var i = 0
        do {
            val result = simulateDropOnGrid(grid, PointYX(0, 500))
            i += if (result)1 else 0
            whenDebug {
                println(i)
                println(grid.toString())
                println()
            }
        } while(result)
        println(grid.toString())
        return i
    }

    fun partTwoSolution(lines: List<String>): Int {
        var paths = linesToPaths(lines).toMutableList()
        var gp = getGridPropertiesFromPoints(paths.flatten()+Pair(500,0))
        val newH = gp.maxY+2
        paths.add(listOf(Pair(gp.minX-newH, newH),Pair(gp.maxX+newH, newH)))
        gp = getGridPropertiesFromPoints(paths.flatten()+Pair(500,0))
        val grid = Grid(gp)
        paths.forEach{grid.drawLines(it,'#')}
        var i = 1
        do {
            val result = simulateDropOnGrid(grid, PointYX(0, 500))
            i += if (result)1 else 0
        } while(result)
        println(grid.toString())
        return i
    }

}