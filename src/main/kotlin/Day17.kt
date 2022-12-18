import java.util.LinkedList
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.absoluteValue

class Day17 {
    class Tile(private val points: List<PointYX>) {
        val height = points.maxOf { it.y } + 1
        val weight = points.maxOf { it.y } + 1
        val position = PointYX()

        fun getPoints(): List<PointYX> {
            return points.map { it.applyDelta(position) }
        }

        fun bottomRowIndex(): Int {
            return position.y + height - 1
        }

        fun left(): Tile {
            position.inplaceDeltaX(-1)
            return this
        }

        fun right(): Tile {
            position.inplaceDeltaX(1)
            return this
        }

        fun down(): Tile {
            position.inplaceDeltaY(1)
            return this
        }

        fun up(): Tile {
            position.inplaceDeltaY(-1)
            return this
        }

        fun clone(): Tile {
            return Tile(points)
        }
    }

    class Tiles {
        val _tiles = listOf(
            """####""",
            """.#.
###
.#.""",

            """..#
..#
###""",

            """#
#
#
#""",
            """##
##"""
        )
        val tiles = stringTilesToTiles(_tiles)

        fun stringTilesToTiles(list: List<String>): List<Tile> {
            val out = list.map {
                val l = it.lines().flatMapIndexed { y, l ->
                    l.mapIndexedNotNull { x, c ->
                        if (c == '#') {
                            PointYX(y, x)
                        } else null
                    }
                }
                Tile(l)
            }
            return out
        }

        fun tilesSequence() = sequence {
            var i = 0
            while (true) {
                yield(Pair(i, tiles[i]))
                i = if (i == tiles.lastIndex) 0 else i + 1
            }
        }

    }

    fun jetsSequence(pattern: String) = sequence {
        var i = 0
        while (true) {
            yield(Pair(i, pattern[i]))
            i = if (i == pattern.lastIndex) 0 else i + 1
        }
    }

    class Chamber(
        val tilesIterator: Iterator<Pair<Int, Tile>>,
        val jetsIterator: Iterator<Pair<Int, Char>>,
        val dropTimesTarget: Long
    ) {
        /*
        Top of chamber is at y=0
         */
        var chamber = LinkedList<MutableList<Char>>()

        private var cutHeight = 0L
        private fun getCleanRow(): MutableList<Char> {
            return mutableListOf('.', '.', '.', '.', '.', '.', '.')
        }

        fun getHeight(): Long {
            return (chamber.size - getTheHighestIndex()) + cutHeight
        }

        override fun toString(): String {
            val body = chamber.joinToString(separator = "\n")
            { "|" + it.joinToString("") + "|" }
            val out = "$body\n+-------+"
            return out
        }

        fun getTheHighestIndex(): Int {// y=0 is top
            for (y in chamber.indices) {
                for (c in chamber[y]) {
                    if (c != '.') {
                        return y
                    }
                }
            }
            return 0
        }

        fun riseChamberFromHighestPoint(newRoom: Int) {
            val highIndex = getTheHighestIndex()
            val diff = newRoom - highIndex
            when {
                diff > 0 ->
                    repeat(diff) {
                        chamber.add(0, getCleanRow())
                    }

                diff < 0 -> repeat(diff.absoluteValue) {
                    chamber.removeAt(0)
                }
            }
        }

        fun chamberIsBlockedAtRow(row: Int): Boolean {
            for (x in chamber[0].indices) {
                if (!hasObstacleAt(row, x) && !hasObstacleAt(row + 1, x)) {
                    return false
                }
            }
            return true
        }

        fun cutHeight(row: Int) {
            val limitHeight = row + 1

            val sizeDiff = chamber.size - limitHeight
            repeat(sizeDiff) { chamber.removeLast() }
            cutHeight += sizeDiff
        }

        val dropIndexAtomic = AtomicLong(0)
        fun run() {
            while (dropIndexAtomic.get() in 0 until dropTimesTarget) {
                val tile = tilesIterator.next()
                dropTile(tile)
                dropIndexAtomic.incrementAndGet()
            }
        }

        fun hasObstacleAt(p: PointYX): Boolean {
            return hasObstacleAt(p.y, p.x)
        }

        private fun hasObstacleAt(y: Int, x: Int): Boolean {
            val free = (y in 0..chamber.lastIndex) &&
                    (x in 0..chamber[0].lastIndex) &&
                    freeAtCorrectCoordinate(y, x)
            return !free
        }

        private fun freeAtCorrectCoordinate(y: Int, x: Int): Boolean {
            return chamber[y][x] == '.'
        }

        fun dropTile(tilePair: Pair<Int, Tile>) {
            var go = true
            val tile = tilePair.second.clone()
            riseChamberFromHighestPoint(tile.height + 3)
            tile.right().right()
            var jetPair: Pair<Int, Char> = Pair(-1, 'X')
            while (go) { // loop of tile movement
                // >>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>
                // move left and right by jet
                jetPair = jetsIterator.next()
                if (jetPair.second == '>') {
                    tile.right()
                    if (tile.getPoints()
                            .any { hasObstacleAt(it) }
                    ) {
                        tile.left()
                    }
                } else {
                    tile.left()
                    if (tile.getPoints()
                            .any { hasObstacleAt(it) }
                    ) {
                        tile.right()
                    }
                }
                // move down
                tile.down()
                if (tile.getPoints()
                        .any { hasObstacleAt(it) }
                ) {
                    tile.up()
                    go = false
                }
            }
            // fix position on chamber
            tile.getPoints().forEach {
                chamber[it.y][it.x] = '#'
            }
            // cut height if tile closed
            val row = tile.bottomRowIndex()
            if (chamberIsBlockedAtRow(row)) {
                cutHeight(row)
                preserveStateAndSearchLoopPattern(tilePair.first, jetPair.first)

            }
        }

        var searching = true
        fun preserveStateAndSearchLoopPattern(tileIndex: Int, jetIndex: Int) {
            if (searching) {
                val m: Map<String, Number> = mapOf(
                    Pair("dropIndex", dropIndexAtomic.get()),
                    Pair("tileIndex", tileIndex),
                    Pair("jetIndex", jetIndex),
                    Pair("curHeight", this.getHeight())
                )
                val h = this.hashCode()
                hashes.compute(h) { k, v -> if (v == null) mutableListOf(m) else v + m }

                val states = hashes[h]!!
                if (states.size >= 3) {
                    // check deltas
                    val set = states.windowed(2).map {
                        val f = it.first()
                        val l = it.last()
                        listOf(
                            l["dropIndex"]!!.toInt() - f["dropIndex"]!!.toInt(),
                            l["tileIndex"]!!.toInt() - f["tileIndex"]!!.toInt(),
                            l["jetIndex"]!!.toInt() - f["jetIndex"]!!.toInt(),
                            l["curHeight"]!!.toInt() - f["curHeight"]!!.toInt()
                        )
                    }.toSet()
                    if (set.size == 1) {
                        // pattern is found!!
                        searching = false
                        whenDebug {
                            println("$h ... ${hashes[h]}")
                        }
                        val dropIndexDelta = set.first()[0]
                        val heightDelta = set.first()[3]

                        val remainingDrops = dropTimesTarget-dropIndexAtomic.get()
                        val amountOfSkippedLoops = remainingDrops.div(dropIndexDelta)
                        val rem = remainingDrops.mod(dropIndexDelta) // remainder
                        dropIndexAtomic.set( dropTimesTarget-rem )
                        cutHeight+=(heightDelta*amountOfSkippedLoops)
                    }
                }

            }

        }

        val hashes = mutableMapOf<Int, List<Map<String, Number>>>()
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Chamber

            if (chamber != other.chamber) return false

            return true
        }

        override fun hashCode(): Int {
            return chamber.hashCode()
        }

    }


    fun partOneSolution(line: String): Long = partOneSolution(line, 2022)
    fun partTwoSolution(line: String): Long = partOneSolution(line, 1000000000000)

    //   1000000000000 * (46/3600)
    fun partOneSolution(line: String, dropTimes: Long): Long {
        val chamber = Chamber(
            Tiles().tilesSequence().iterator(),
            jetsSequence(line).iterator(),
            dropTimes
        )
        chamber.run()
        val out = chamber.getHeight()
        return out
    }


}