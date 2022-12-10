class Grid(val list: Iterable<Pair<Int, Int>>) {
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

    fun drawPoint(x: Int, y:Int,c:Char):Grid{
        plane[y-minY][x-minX]=c
        return this
    }

    fun drawPoint(p:Pair<Int,Int>,c:Char):Grid{
        drawPoint(p.first, p.second, c)
        return this
    }

    fun drawPoints(points:Iterable<Pair<Int,Int>>,c:Char):Grid{
        points.forEach{
            drawPoint(it,c)
        }
        return this
    }

    override fun toString():String{
        return array2dToString(plane)
    }

    fun drawOriginalPoints(): Grid {
        drawPoints(list,'#')
        return this
    }

    fun drawOriginalEnumeratedPoints(): Grid {
        list.forEachIndexed{ i,p-> drawPoint(p, i.toString()[0]) }
        return this
    }
}