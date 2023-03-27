import java.util.*

class Day24(val lines: List<String>) {
    val blizzards: State = State(lines.flatMapIndexed { y, line ->
        line.toCharArray().mapIndexed { x, c ->
            if (c != '.' && c != '#') {
                PositionAndDirection(y, x, c)
            } else {
                null
            }
        }.filterNotNull()
    }.toSet(), lines.size, lines.first().length)
    val start = PointYX(
        0,
        lines.first().mapIndexedNotNull { i, it ->
            if (it == '.') i else null
        }.first()
    )
    val finish = PointYX(
        lines.lastIndex,
        lines.last().mapIndexedNotNull { i, it ->
            if (it == '.') i else null
        }.first()
    )


    fun isInBorders(p: PointYX): Boolean {
        val out = p.y in 0..lines.lastIndex &&
                p.x in 0..lines.first().lastIndex &&
                lines[p.y][p.x] != '#'
        return out
    }

    class State(private val blizzards: Set<PositionAndDirection>, val height:Int, val width:Int) {
        private val occupiedPositions = blizzards.map { it.position }.toSet()
        fun occupied(c:PositionAndDirection): Boolean {
            return c.position in occupiedPositions
        }
        fun occupied(c:PointYX): Boolean {
            return c in occupiedPositions
        }
        fun addHero(point:PointYX):StateWithHero{
            return StateWithHero(this, point)
        }

        fun moveBlizzards(): State {
            return State(blizzards.map {
                var newP = it.stepForward()
                newP = shiftOnBorder(newP)
                newP
            }.toSet(), height, width)
        }

        fun shiftOnBorder(p: PositionAndDirection): PositionAndDirection {
            val out = when {
                p.position.y <= 0 -> p.setY(height - 2)
                p.position.y > height-2 -> p.setY(1)
                p.position.x <= 0 -> p.setX(width - 2)
                p.position.x > width-2 -> p.setX(1)
                else -> p
            }
            return out
        }
    }
    class StateWithHero(state:State, hero:PointYX)

    class States(state: State, hero: PointYX){
        val states = mutableSetOf(state)
        val visited: MutableSet<StateWithHero> = mutableSetOf(state.addHero(hero))
        var lastState: State = state
        var lastIndex = 0
        fun index():Int{
            return lastIndex
        }
        fun lastState():State {
            return lastState
        }
        fun visitedCount():Int{
            return visited.size
        }
        fun addVisited(hero:PointYX):Boolean{
            return visited.add(lastState.addHero(hero))
        }

        fun moveBlizzards() {
            lastState = lastState.moveBlizzards()
            states.add(lastState)
            lastIndex++
        }

        fun cleanVisited() {
            visited.clear()
            lastIndex = 0
        }
    }

/*
blizzard position to check coordinates
blizzards to check system state
 */
    fun searchPathMinutes(path:List<PointYX>): Int {
        val states = States(blizzards, path.first())
        val out = path.windowed(2).mapIndexed { index, (from,to) ->
            states.cleanVisited()
            var minutes = searchPathMinutes(from, to, states) + (if (index==0) 0 else 1)
            println("$index. from=$from, to=$to, minutes=$minutes")
            minutes
        }

        return out.sum();
    }
    fun searchPathMinutes(from:PointYX,to:PointYX,states:States): Int {
        val q = mutableSetOf<PointYX>()
        q.add(from)
        while (q.isNotEmpty()) {

            // before level processing
            var levelSize = q.size
//            println("${states.index()}. visited=${states.visitedCount()}. levelSize=${levelSize}.")
            while (levelSize-- > 0) {
                val c = q.iterator().next()
                q.remove(c)
                if (c == to) {
                    return states.index() - 1;
                }
                if (states.addVisited(c)) {
                    val nextPoints = (c.getManhattanNeighbours() + c)
                        .filter { n -> isInBorders(n) }
                        .filter { n -> ! states.lastState().occupied(n) }
                    nextPoints.forEach { q.add(it) }
                }
            }

            states.moveBlizzards()
        }
        throw Error("path not found")
    }

    fun solvePart1(): Int {
        return searchPathMinutes(listOf(start,finish))
    }
    fun solvePart2(): Int {
        return searchPathMinutes(listOf(start,finish,start,finish))
    }
}