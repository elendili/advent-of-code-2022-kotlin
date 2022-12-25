class Day22 {

    fun linesToMapAndInstruction(lines:List<String>): Pair<List<String>, List<String>> {
        val instrs = lineToInstructions(lines.last())
        val mapStrs = lines.dropLast(2)
        return Pair(mapStrs,instrs)
    }

    fun wrap(map:List<String>, positionAndDirection:PositionAndDirection):PositionAndDirection{
        val delta = positionAndDirection.direction
        val p = positionAndDirection.position
        when(delta){
            downDelta -> {
                for(y in 0..map.lastIndex){
                    if(p.x<map[y].length) {
                        if (map[y][p.x] == '#') {
                            return positionAndDirection
                        }
                        if (map[y][p.x] == '.') {
                            return PositionAndDirection(PointYX(y, p.x), delta)
                        }
                    }
                }
            }
            upDelta -> {
                for(y in map.lastIndex downTo 0){
                    if(p.x<map[y].length) {
                        if (map[y][p.x] == '#') {
                            return positionAndDirection
                        }
                        if (map[y][p.x] == '.') {
                            return PositionAndDirection(PointYX(y, p.x), delta)
                        }
                    }
                }
            }
            leftDelta->{
                for(x in map[p.y].lastIndex downTo 0){
                    if(map[p.y][x]=='#'){
                        return positionAndDirection
                    }
                    if(map[p.y][x]=='.'){
                        return PositionAndDirection(PointYX(p.y,x), delta)
                    }
                }
            }
            rightDelta->{
                for(x in 0..map[p.y].lastIndex){
                    if(map[p.y][x]=='#'){
                        return positionAndDirection
                    }
                    if(map[p.y][x]=='.'){
                        return PositionAndDirection(PointYX(p.y,x), delta)
                    }
                }
            }
        }
        error("roll position not found")
    }

    fun go(map:List<String>, steps:Int, positionAndDirection:PositionAndDirection): PositionAndDirection {
        var pad = positionAndDirection
        for(i in 1..steps){
            var np = pad.stepForward()
            if(np.position.y !in 0..map.lastIndex
                || np.position.x !in 0..map[np.position.y].lastIndex
                || map[np.position.y][np.position.x]==' '){
                np = wrap(map,pad)
            }

            val npv = map[np.position.y][np.position.x]
            if (npv == '#'){
                return pad
            } else {
                pad = np
            }
        }
        return pad
    }

    fun lineToInstructions(l:String):List<String>{
        val out = mutableListOf<String>()
        var tmp = ""
        l.forEachIndexed{i,it->
            when{
                it=='R' || it=='L' -> {
                    out.add(tmp)
                    tmp=""
                    out.add(it.toString())
                }
                else -> {
                    tmp+=it
                }
            }
            if(i==l.lastIndex){
                out.add(tmp)
            }
        }
        return out
    }

    fun finalEval(positionAndDirection: PositionAndDirection):Int{
        val ed = when(positionAndDirection.direction){
            rightDelta->0
            downDelta->1
            leftDelta->2
            upDelta->3
            else -> error("unknown direction ${positionAndDirection.direction}")
        }
        val out = 1000*(positionAndDirection.position.y+1) +
                4*(positionAndDirection.position.x+1) +
                ed
        return out
    }

    fun getStartPoint(map: List<String>):PointYX{
        for(x in 0..map[0].lastIndex){
            if(map[0][x]=='.'){
                return PointYX(0,x)
            }
        }
        error("No start point available")
    }

    fun followInstructions(map:List<String>, instructions:List<String>): PositionAndDirection {
        val position = getStartPoint(map)
        var positionAndDirection = PositionAndDirection(position)
        instructions.forEach {
            if(it[0].isDigit()){
                positionAndDirection = go(map,it.toInt(), positionAndDirection)
            } else {
                if(it[0]=='R') {
                    positionAndDirection = positionAndDirection.rotateRight()
                } else {
                    positionAndDirection = positionAndDirection.rotateLeft()
                }
            }
        }
        return positionAndDirection
    }

    fun partOneSolution(lines: List<String>): Int {
        val (map,instrs) = linesToMapAndInstruction(lines)
        val pad = followInstructions(map,instrs)
        val out = finalEval(pad)
        return out
    }

    fun partTwoSolution(lines: List<String>): Int {
        val (map,instrs) = linesToMapAndInstruction(lines)
        val pad = followInstructions(map,instrs)
        val out = finalEval(pad)
        return out
    }



}