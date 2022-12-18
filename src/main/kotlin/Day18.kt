import java.lang.Integer.max
import java.lang.Integer.min
import java.util.*

class Day18 {
    fun linesToCubes(lines: List<String>):Set<PointXYZ>{
        return lines.map{
            val (x,y,z) = it.split(",").mapToInts()
            PointXYZ(x,y,z)
        }.toSet()
    }

    fun getNotConnectedSidesOfCubes(cubesSet:Set<PointXYZ>): Int {
        val cubes = cubesSet.toList()
        var sidesCount = cubes.size*6
        for(i in 0 until cubes.lastIndex){
            for(j in i..cubes.lastIndex){
                if(cubes[i].cubeSidesConnected(cubes[j])){
                    sidesCount-=2
                }
            }
        }
        return sidesCount
    }

    fun partOneSolution(lines: List<String>): Int {
        val cubes = linesToCubes(lines)
        return getNotConnectedSidesOfCubes(cubes)
    }

    data class SpaceProperties(
        val minX:Int,
        val maxX:Int,
        val minY:Int,
        val maxY:Int,
        val minZ:Int,
        val maxZ:Int,
    )

    fun getSpacePropertiesFromCubes(cubes:Set<PointXYZ>):SpaceProperties{
        var minX:Int=0
        var maxX:Int=0
        var minY:Int=0
        var maxY:Int=0
        var minZ:Int=0
        var maxZ:Int=0
        cubes.forEach {
             minX = min(minX    , it.x)
             maxX = max(maxX    , it.x)
             minY = min(minY    , it.y)
             maxY = max(maxY    , it.y)
             minZ = min(minZ    , it.z)
             maxZ = max(maxZ    , it.z)
        }
        return SpaceProperties(
                            minX-1,
                            maxX+1,
                            minY-1,
                            maxY+1,
                            minZ-1,
                            maxZ+1,
        )
    }
    fun createSpace(spaceProperties: SpaceProperties):Set<PointXYZ>{
        val out = mutableSetOf<PointXYZ>()
        for(x in spaceProperties.minX..spaceProperties.maxX)
            for(y in spaceProperties.minY..spaceProperties.maxY)
                for(z in spaceProperties.minZ..spaceProperties.maxZ) {
                    out.add(PointXYZ(x,y,z))
        }
        return out
    }

    fun unreachableSidesCount(start:PointXYZ, space:Set<PointXYZ>, occupied:Set<PointXYZ>):Int{
        val q = LinkedList<PointXYZ>()
        q.add(start)
        val visited = mutableSetOf<PointXYZ>()
        var sidesCount = 0
        while (q.isNotEmpty()) {
            val e = q.pop()
            listOf(
                PointXYZ(e.x-1,e.y,e.z),
                PointXYZ(e.x+1,e.y,e.z),
                PointXYZ(e.x,e.y-1,e.z),
                PointXYZ(e.x,e.y+1,e.z),
                PointXYZ(e.x,e.y,e.z-1),
                PointXYZ(e.x,e.y,e.z+1),
            )
                .filter {
                    space.contains(it)
                } // not outside of space
                .filter {
                    if(occupied.contains(it)) {
                        sidesCount++
                        false
                    } else true
                } // not outside of space
                .filter { visited.add(it) } // not outside of space
                .forEach {
                    q.add(it)
                }
        }
        return sidesCount
    }

    fun partTwoSolution(lines: List<String>): Int {
        val cubes = linesToCubes(lines)
        val notConnectedSidesOfCubes = getNotConnectedSidesOfCubes(cubes)
        val spaceProperties = getSpacePropertiesFromCubes(cubes)
        val space = createSpace(spaceProperties)
        val origin = PointXYZ(spaceProperties.minX,
            spaceProperties.minY,
            spaceProperties.minZ)
        val unreachableSidesCount = unreachableSidesCount(origin,space,cubes)
        return unreachableSidesCount
    }


}