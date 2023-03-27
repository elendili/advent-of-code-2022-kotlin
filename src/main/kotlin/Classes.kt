import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.absoluteValue

data class GridProperties(val width: Int, val height: Int,
                          val minX: Int, val minY: Int,
                          val maxX: Int, val maxY: Int
                          )

fun getGridPropertiesFromYXs(xs: Iterable<Int>, ys: Iterable<Int>): GridProperties {
    val maxX = xs.max()
    val minX = xs.min()
    val maxY = ys.max()
    val minY = ys.min()
    val widthOfPlane = maxX - minX + 1
    val heightOfPlane = maxY - minY + 1
    return GridProperties(widthOfPlane, heightOfPlane, minX, minY, maxX, maxY)
}

fun getGridPropertiesFromPairs(list: Iterable<Pair<Int, Int>>): GridProperties {
    val xs = list.map { it.first }
    val ys = list.map { it.second }
    return getGridPropertiesFromYXs(xs,ys)
}

fun getGridPropertiesFromPointYXs(list: Iterable<PointYX>): GridProperties {
    val xs = list.map { it.x }
    val ys = list.map { it.y }
    return getGridPropertiesFromYXs(xs,ys)
}

class Grid(val gridProperties: GridProperties) {
    val plane: Array<Array<Char>> = Array(gridProperties.height) { Array(gridProperties.width) { '.' } }

    private fun yOnGrid(y:Int):Int{
        return y - gridProperties.minY
    }

    private fun xOnGrid(x:Int):Int{
        return x - gridProperties.minX
    }

    fun drawPoint(x: Int, y: Int, c: Char): Grid {
        plane[yOnGrid(y)][xOnGrid(x)] = c
        return this
    }
    fun getPoint(p:PointYX): Char {
        return getPoint(p.x, p.y)
    }
    fun getPoint(x: Int, y: Int): Char {
        return plane[yOnGrid(y)][xOnGrid(x)]
    }

    fun getPoint(p:Pair<Int, Int>): Char {
        return getPoint(p.first, p.second)
    }

    fun drawPoint(p: Pair<Int, Int>, c: Char): Grid {
        drawPoint(p.first, p.second, c)
        return this
    }

    fun drawPoints(points: Iterable<Pair<Int, Int>>, c: Char): Grid {
        points.forEach {
            drawPoint(it, c)
        }
        return this
    }

    fun drawLines(points: Iterable<Pair<Int, Int>>, c: Char): Grid {
        points.windowed(2).forEach {
            val fi = it.first()
            val la = it.last()
            if (fi.first != la.first) {
                assert(fi.second == la.second)
                val start = min(fi.first, la.first)
                val end = max(fi.first, la.first)
                for (i in start..end) {
                    drawPoint(i, fi.second, c)
                }
            } else if (fi.second != la.second) {
                assert(fi.first == la.first)
                val start = min(fi.second, la.second)
                val end = max(fi.second, la.second)
                for (i in start..end) {
                    drawPoint(fi.first, i, c)
                }
            }
        }
        return this
    }

    override fun toString(): String {
        return plane.joinToString("\n") { it.joinToString("") }
    }

    fun drawEnumeratedPoints(list: Iterable<Pair<Int, Int>>): Grid {
        list.forEachIndexed { i, p -> drawPoint(p, i.toString()[0]) }
        return this
    }

    fun drawPoint(p: PointYX, c:Char) {
        drawPoint(p.x, p.y, c)
    }

    fun isOnGrid(p: PointYX): Boolean {
        return yOnGrid(p.y) in plane.indices
                && xOnGrid(p.x) in plane[0].indices
    }

    fun isOnEdge(p: PointYX): Boolean {
        return yOnGrid(p.y) ==0 || yOnGrid(p.y) == plane.lastIndex
                || xOnGrid(p.x) == 0 || xOnGrid(p.x) == plane[0].lastIndex
    }
}

class PointXYZ(val x: Int = 0, val y: Int = 0, val z:Int = 0) {
    fun cubeSidesConnected(p: PointXYZ): Boolean {
        val delta = listOf(
            (x-p.x).absoluteValue,
            (y-p.y).absoluteValue,
            (z-p.z).absoluteValue
        )
        return when(delta) {
            listOf(1, 0, 0) -> true
            listOf(0, 1, 0) -> true
            listOf(0, 0, 1) -> true
            else -> false
        }
    }

    override fun toString(): String {
        return "(x=$x,y=$y,z=$z)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PointXYZ

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + z
        return result
    }

}

val downDirection = PointYX(1,0)
val upDirection = PointYX(-1,0)
val rightDirection = PointYX(0,1)
val leftDirection = PointYX(0,-1)
data class PositionAndDirection(val position:PointYX, val direction:PointYX=rightDirection){
    constructor(y:Int, x:Int, dirChar:Char) : this(PointYX(y,x),dirChar)
    constructor(y:Int, x:Int, dir:PointYX) : this(PointYX(y,x),dir)
    constructor(position:PointYX, dirChar:Char) :
            this(position,when(dirChar){
                '>'->rightDirection
                '<'->leftDirection
                '^'->upDirection
                'v'->downDirection
                else -> throw Error("Unknown char direction '$dirChar'")
            })
    fun stepForward():PositionAndDirection{
        return PositionAndDirection(position.applyDelta(direction),direction)
    }
    fun setX(x:Int):PositionAndDirection{
        return PositionAndDirection(position.y, x, direction)
    }
    fun setY(y:Int):PositionAndDirection{
        return PositionAndDirection(y, position.x, direction)
    }
    fun rotateRight():PositionAndDirection{
        val newD = when(direction) {
            downDirection-> leftDirection
            upDirection-> rightDirection
            rightDirection-> downDirection
            leftDirection-> upDirection
            else -> error("Unknown delta: $direction")
        }
        return PositionAndDirection(position,newD)
    }

    fun rotateLeft():PositionAndDirection{
        val newD = when(direction) {
            downDirection-> rightDirection
            upDirection-> leftDirection
            rightDirection-> upDirection
            leftDirection-> downDirection
            else -> error("Unknown delta: $direction")
        }
        return PositionAndDirection(position,newD)
    }
}
class PointYX(var y: Int = 0, var x: Int = 0, val backTrack: PointYX? = null) {
    fun x(v: Int) {
        this.x = v
    }

    fun y(v: Int) {
        this.y = v
    }

    fun deltaX(dx: Int) = PointYX(y, x + dx, this)
    fun deltaY(dy: Int) = PointYX(y + dy, x, this)
    fun applyDelta(d: PointYX) = PointYX(y + d.y, x+d.x, this)

    fun inplaceDeltaX(dx: Int) {
        this.x += dx
    }

    fun inplaceDeltaY(dy: Int) {
        this.y += dy
    }

    fun inplaceDelta(p: PointYX) {
        inplaceDeltaX(p.x)
        inplaceDeltaY(p.y)
    }

    fun distanceTo(p: PointYX): Int {
        return (y - p.y).absoluteValue + (x - p.x).absoluteValue
    }

    fun getValueFromGrid(g: List<String>): Char {
        return g[y][x]

    }

    fun getPathToStart(): List<PointYX> {
        val out = mutableListOf<PointYX>()
        out.add(this)
        while (out.last().backTrack != null) {
            out.add(out.last().backTrack!!)
        }
        return out
    }

    fun getPathToCurrent(): List<PointYX> {
        return getPathToStart().reversed()
    }

    fun getManhattanNeighbours(): List<PointYX> {
        return listOf(
            PointYX(y-1,x),
            PointYX(y+1,x),
            PointYX(y, x-1),
            PointYX(y, x+1),
        )
    }
    fun getDiagonalNeighbours(): List<PointYX> {
        return listOf(
            PointYX(y-1,x-1),
            PointYX(y-1,x+1),
            PointYX(y+1, x-1),
            PointYX(y+1, x+1),
        )
    }
    fun getAllNeighbours(): List<PointYX>{
        return getManhattanNeighbours().plus(getDiagonalNeighbours())
    }
    // north, south, west, east
    fun getNeighboursBySide(): List<List<PointYX>> {
        return listOf(
            listOf(//north
                PointYX(y-1,x-1),
                PointYX(y-1,x),
                PointYX(y-1,x+1)
            ),
            listOf(//south
                PointYX(y+1,x-1),
                PointYX(y+1,x),
                PointYX(y+1,x+1)
            ),
            listOf( // west
                PointYX(y-1,x-1),
                PointYX(y,  x-1),
                PointYX(y+1,x-1)
            ),
            listOf( // east
                PointYX(y-1,x+1),
                PointYX(y,  x+1),
                PointYX(y+1,x+1)
            )
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PointYX

        if (y != other.y) return false
        if (x != other.x) return false

        return true
    }

    override fun hashCode(): Int {
        var result = y
        result = 31 * result + x
        return result
    }

    override fun toString(): String {
        return "PointYX(y=$y, x=$x)"
    }

    operator fun minus(p: PointYX): PointYX {
        return PointYX(this.y - p.y, this.x - p.x)
    }
}