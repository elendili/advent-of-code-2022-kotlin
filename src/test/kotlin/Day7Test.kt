import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day7Test {
    private val input = """$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k""".lines()

    @Test
    fun linesToFsTest() {
        assertEquals("0:{a=0:{e=0:{i=584}, f=29116, g=2557, h.lst=62596}, b.txt=14848514, c.dat=8504156, d=0:{j=4060174, d.log=8033020, d.ext=5626152, k=7214296}}",
            Day7().linesToFs(input).toString())
        assertEquals("48381165:{a=94853:{e=584:{i=584}, f=29116, g=2557, h.lst=62596}, b.txt=14848514, c.dat=8504156, d=24933642:{j=4060174, d.log=8033020, d.ext=5626152, k=7214296}}",
            Day7().recursiveSetDirSizes(Day7().linesToFs(input)).toString())
    }

    @Test
    fun testSolution() {
        assertEquals(95437, Day7().partOneSolution(input))
        assertEquals(24933642, Day7().partTwoSolution(input))
    }

    @Test
    fun solution() {
        val lines = getResourceAsLines("Day7_data.txt")
        assertEquals(1611443,Day7().partOneSolution(lines))
        assertEquals(2086088,Day7().partTwoSolution(lines))
    }
}