/*
https://adventofcode.com/2022/day/3
 */

class Day7 {
    data class FsElement(
                         val name:String = "/",
                         val type:String = "dir",
                         val children:MutableMap<String, FsElement> = mutableMapOf(),
                         val parent: FsElement? = null,
                         var size:Int = 0){
        override fun toString(): String = "$size${ if(children.isNotEmpty()) ":$children" else "" }"
        fun isDir():Boolean = type == "dir"
    }

    fun linesToFs(lines: List<String>):FsElement{
        val root = FsElement()
        var current:FsElement = root
        lines.drop(1).forEach{ line ->
            when {
                line == "\$ ls" -> {}
                line == "\$ cd .." -> {
                    current = current.parent!!
                }
                line.startsWith("\$ cd ") -> {
                    val dirName = line.substringAfter(" cd ")
                    current.children.computeIfAbsent(dirName){FsElement(it, parent = current)}
                    current = current.children[dirName]!!
                }
                line.startsWith("dir ") ->{
                    val dirName = line.substringAfter(" ")
                    current.children.computeIfAbsent(dirName){FsElement(it, parent = current)}
                }
                else -> {
                    // for files
                    val fileName = line.substringAfter(" ")
                    val fileSize = line.substringBefore(" ").toInt()
                    current.children.computeIfAbsent(fileName){
                        FsElement(it, type="file", parent = current, size = fileSize)
                    }
                }
            }
        }
        return root
    }

    fun recursiveSetDirSizes(fs:FsElement):FsElement {
        for(item in fs.children.values){
            if(item.isDir()) { // file
                recursiveSetDirSizes(item)
            }
            fs.size=item.size+fs.size
        }
        return fs
    }

    fun listAllElements(fs:FsElement):List<FsElement> {
        val out = mutableListOf<FsElement>()
        val q = mutableListOf(fs)
        while (q.isNotEmpty()){
            val item = q.removeFirst()
            out.add(item)
            if(item.isDir()){
                item.children.values.forEach{q.add(it)}
            }
        }
        return out
    }

    fun partOneSolution(lines: List<String>): Int {
        val fs = linesToFs(lines)
        recursiveSetDirSizes(fs)
        // find dirs with size at most 100000
        val allFSE = listAllElements(fs)
        val filteredFSE = allFSE
            .filter { it.isDir() }
            .filter { it.size <= 100_000 }
        val out = filteredFSE.sumOf { it.size }
        return out
    }

    fun getSpaceToBeFreedForRequiredSpaceOnDisk(fs:FsElement, totalSpace:Int, requiredSpace:Int):Int {
        val usedSpace = fs.size
        val freeSpace = totalSpace-usedSpace
        val spaceToBeFreed = requiredSpace-freeSpace
        return spaceToBeFreed
    }

    fun findSmallestDirWhichBiggerOrEqualInSizeToRequested(fs:FsElement, requiredSpace:Int):FsElement {
        return listAllElements(fs)
            .filter { it.isDir() }
            .filter { it.size>=requiredSpace }
            .minBy { it.size }
    }

    fun partTwoSolution(lines: List<String>): Int {
        val fs = linesToFs(lines)
        recursiveSetDirSizes(fs)
        val toBeFreed = getSpaceToBeFreedForRequiredSpaceOnDisk(fs, 70000000, 30000000)
        val d = findSmallestDirWhichBiggerOrEqualInSizeToRequested(fs, toBeFreed)
        return d.size
    }

}