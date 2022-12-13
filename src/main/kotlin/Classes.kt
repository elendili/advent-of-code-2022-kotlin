import kotlin.math.absoluteValue

class GridForPath(val list: Iterable<Pair<Int, Int>>) {
    val plane:Array<Array<Any>>
    val minX:Int
    val minY:Int

    init {
        val xs = list.map { it.first }
        val ys = list.map { it.second }
        val maxX = xs.max()
        minX = xs.min()
        val maxY = ys.max()
        minY = ys.min()
        val widthOfPlane = maxX - minX + 1
        val heightOfPlane = maxY - minY +1
        plane = Array(heightOfPlane) { Array(widthOfPlane) { '.' } }
    }

    fun drawPoint(x: Int, y:Int,c:Char):GridForPath{
        plane[y-minY][x-minX]=c
        return this
    }

    fun drawPoint(p:Pair<Int,Int>,c:Char):GridForPath{
        drawPoint(p.first, p.second, c)
        return this
    }

    fun drawPoints(points:Iterable<Pair<Int,Int>>,c:Char):GridForPath{
        points.forEach{
            drawPoint(it,c)
        }
        return this
    }

    override fun toString():String{
        return array2dToString(plane)
    }

    fun drawOriginalPoints(): GridForPath {
        drawPoints(list,'#')
        return this
    }

    fun drawOriginalEnumeratedPoints(): GridForPath {
        list.forEachIndexed{ i,p-> drawPoint(p, i.toString()[0]) }
        return this
    }
}

class PointYX(var y:Int=0, var x:Int=0, val backTrack:PointYX?=null){
    fun x(v:Int){
        this.x=v
    }
    fun y(v:Int){
        this.y=v
    }

    fun deltaX(dx:Int) = PointYX(y,x+dx,this)
    fun deltaY(dy:Int) = PointYX(y+dy,x,this)

    fun inplaceDeltaX(dx:Int){
        this.x+=dx
    }
    fun inplaceDeltaY(dy:Int){
        this.y+=dy
    }
    fun inplaceDelta(p:PointYX){
        inplaceDeltaX(p.x)
        inplaceDeltaY(p.y)
    }
    fun distanceTo(p:PointYX):Int{
        return (y-p.y).absoluteValue + (x-p.x).absoluteValue
    }

    fun getValueFromGrid(g:List<String>):Char{
        return g[y][x]

    }

    fun getPathToStart():List<PointYX>{
        val out = mutableListOf<PointYX>()
        out.add(this)
        while(out.last().backTrack!=null){
            out.add(out.last().backTrack!!)
        }
        return out
    }
    fun getPathToCurrent():List<PointYX>{
        return getPathToStart().reversed()
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

    operator fun minus(p:PointYX): PointYX {
        return PointYX(this.y-p.y,this.x-p.x)
    }
}