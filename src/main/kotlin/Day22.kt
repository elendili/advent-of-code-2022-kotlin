class Day22 {

    fun linesToMapAndInstruction(lines:List<String>): Pair<List<String>, List<String>> {
        val instrs = lineToInstructions(lines.last())
        val mapStrs = lines.dropLast(2)
        return Pair(mapStrs,instrs)
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
            rightDirection->0
            downDirection->1
            leftDirection->2
            upDirection->3
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

    fun wrapAsCube(map:List<String>,
                   pad:PositionAndDirection
    ):PositionAndDirection{
        // calc cube side length
        val sl = map[0].substringAfter(" ").length/3
/*        12 edges - 4 existing = 8 transitions
        1<->3
            1->3: leftDirection -> downDirection
                  x<= cubeSideLength*2
                  x=cubeSideLength+y
            3->1: upDirection -> rightDirection
                  y<=cubeSideLength
                  y=x-cubeSideLength
        1<->2
            1->2: upDirection -> downDirection
        1<->6


        1111
        1111
        1111
        1111
222233334444
222233334444
222233334444
222233334444
        55556666
        55556666
        55556666
        55556666
         */
        val y = pad.position.y
        val x = pad.position.x
        val out:PositionAndDirection = when{
            // 1 ===========================================
            (x in sl*2 until sl*3 && y in 0 until sl) -> {
                // 1
                when(pad.direction){
                    // 2
                    upDirection->
                        PositionAndDirection(
                            PointYX(sl, sl-(x-sl*2) ),
                            downDirection)
                    // 3
                    leftDirection->
                        PositionAndDirection(
                            PointYX(sl, sl+y),
                            downDirection)
                    // 6
                    rightDirection->
                        PositionAndDirection(
                            PointYX(sl*3-y, sl*4-1),
                            leftDirection)
                    else -> error("unexpected branch")
                }
            }
            // 2 ===========================================
            (x in 0 until sl && y in sl until sl*2) -> {
                when (pad.direction) {
                    // 1
                    upDirection->
                        PositionAndDirection(
                            PointYX(0, sl*3-x-1),
                            downDirection)
                    // 6
                    leftDirection->
                        PositionAndDirection(
                            PointYX(sl*3-1, sl*3+y),
                            upDirection)
                    // 5
                    downDirection->
                        PositionAndDirection(
                            PointYX(sl*3-1, sl*2+(sl-y-1)),
                            upDirection)
                    else -> error("unexpected branch")
                }
            }
            // 3 ===========================================
            (x in sl until sl*2 && y in sl until sl*2) -> {
                when (pad.direction) {
                    // 1
                    upDirection->
                        PositionAndDirection(
                            PointYX(x-sl, sl*2),
                            rightDirection)
                    // 5
                    downDirection->
                        PositionAndDirection(
                            PointYX(sl*3-1, sl*2+(sl-y-1)),
                            upDirection)
                    else -> error("unexpected branch")
                }
            }
            // 4 ===========================================
            (x in sl*2 until sl*3 && y in sl until sl*2) -> {
                when (pad.direction) {
                    // 6
                    rightDirection->
                        PositionAndDirection(
                            PointYX(sl*2, sl*5-y),
                            downDirection)
                    else -> error("unexpected branch")
                }
            }
            // 5
            (x in sl*2 until sl*3 && y in sl*2 until sl*3) -> {
                when (pad.direction) {
                    // 2
                    downDirection->
                        PositionAndDirection(
                            PointYX(sl*2-1, sl-(x-sl*2)),
                            upDirection)
                    // 3
                    leftDirection->
                        PositionAndDirection(
                            PointYX(sl*2-1, sl*2-(y-sl*2) ),
                            upDirection)
                    else -> error("unexpected branch")
                }
            }
            // 6
            (x in sl*2 until sl*3 && y in sl*2 until sl*3) -> {
                when (pad.direction) {
                    // 2
                    downDirection->
                        PositionAndDirection(
                            PointYX( sl+(sl*4-x) , 0),
                            rightDirection)
                    // 4
                    upDirection->
                        PositionAndDirection(
                            PointYX(sl+(sl*4-x), sl*3-1),
                            upDirection)
                    // 1
                    rightDirection->
                        PositionAndDirection(
                            PointYX((sl*3-y), sl*3-1),
                            leftDirection)
                    else -> error("unexpected branch")
                }
            }
            else -> error("unexpected branch")
        }
        return out
    }
    fun wrapAsPlane(map:List<String>, positionAndDirection:PositionAndDirection):PositionAndDirection{
        val delta = positionAndDirection.direction
        val p = positionAndDirection.position
        when(delta){
            downDirection -> {
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
            upDirection -> {
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
            leftDirection->{
                for(x in map[p.y].lastIndex downTo 0){
                    if(map[p.y][x]=='#'){
                        return positionAndDirection
                    }
                    if(map[p.y][x]=='.'){
                        return PositionAndDirection(PointYX(p.y,x), delta)
                    }
                }
            }
            rightDirection->{
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

    fun go(map:List<String>, steps:Int,
           positionAndDirection:PositionAndDirection,
           wrapAction: (List<String>,PositionAndDirection)->PositionAndDirection
    ): PositionAndDirection {
        var pad = positionAndDirection
        for(i in 1..steps){
            var np = pad.stepForward()
            if(np.position.y !in 0..map.lastIndex
                || np.position.x !in 0..map[np.position.y].lastIndex
                || map[np.position.y][np.position.x]==' '){
                np = wrapAction(map,pad)
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

    fun followInstructions(map:List<String>,
                           instructions:List<String>,
                           wrapAction: (List<String>,PositionAndDirection)->PositionAndDirection
    ): PositionAndDirection {
        val position = getStartPoint(map)
        var positionAndDirection = PositionAndDirection(position)
        instructions.forEach {
            if(it[0].isDigit()){
                positionAndDirection = go(map,it.toInt(), positionAndDirection, wrapAction)
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
        val pad = followInstructions(map,instrs, Day22()::wrapAsPlane )
        val out = finalEval(pad)
        return out
    }

    fun partTwoSolution(lines: List<String>): Int {
        val (map,instrs) = linesToMapAndInstruction(lines)
        val pad = followInstructions(map,instrs, Day22()::wrapAsPlane )
        val out = finalEval(pad)
        return out
    }



}