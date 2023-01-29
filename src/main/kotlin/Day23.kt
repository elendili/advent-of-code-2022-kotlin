class Day23(val lines: List<String>) {
    val elves:Set<PointYX> = lines.flatMapIndexed{ y,line ->
        line.toCharArray().mapIndexed{ x, c ->
            if(c=='#') { PointYX(y, x) } else { null }
        }.filterNotNull()
    }.toSet()

    fun getEmptyGroundTilesCount(elves:Set<PointYX>):Int{
        val gridProperties = getGridPropertiesFromPointYXs(elves)
        val out = gridProperties.height * gridProperties.width - elves.size
        return out
    }

    private fun round(round:Int, elves: Set<PointYX>): Set<PointYX> {
        // considering for each elf
        val proposedNewElvesPositions:MutableMap<PointYX,List<PointYX>> = mutableMapOf()
        elves.forEach{elv->
            val newP = nextPosition(round, elves, elv)
            proposedNewElvesPositions.compute(newP) { k, v ->
                if (v == null) mutableListOf(elv) else v + elv }
        }
        val out = mutableSetOf<PointYX>()
        proposedNewElvesPositions.forEach{ (proposedPos, oldPositions) ->
            if(oldPositions.size>1){
                out.addAll(oldPositions)
            } else {
                out.add(proposedPos)
            }
        }
        return out

    }

    private fun nextPosition(round:Int, elves: Set<PointYX>, elf:PointYX):PointYX{
        if (elf.getAllNeighbours().none { e -> e in elves }){
            return elf
        }
        for (i in 0..3){
            val sideIndex = (round+i).mod(4)
            val sidePositions = elf.getNeighboursBySide()[sideIndex]
            if(sidePositions.none { it in elves }){
                return sidePositions[1]
            }
        }
        return elf
    }

    fun solvePart1():Int {
        var localElves = elves.toSet()
        repeat(10){i->
            localElves = round(i,localElves)
        }
        val out = getEmptyGroundTilesCount(localElves)
        return out
    }

    fun solvePart2():Int {
        var localElves = elves.toSet()
        var i = 0
        var changedLocalElves=localElves
        do{
            localElves=changedLocalElves
            changedLocalElves = round(i++,localElves)
        }while(changedLocalElves.toSet() != localElves.toSet())
        return i
    }

}
