import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CommonFunctionsKtTest {

    @Test
    fun combinationsTest() {
        val list = listOf(1,2,3,4).map { it.toString() }.toList()
        assertEquals("[[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]]",
            combinationsOfSize(list, 2).toString())
        assertEquals("[[1, 2, 3], [1, 2, 4], [1, 3, 4], [2, 3, 4]]",
            combinationsOfSize(list, 3).toString())
        assertEquals("[[1], [2], [3], [4]]",
            combinationsOfSize(list, 1).toString())
    }
    @Test
    fun combinationsAllTest() {
        val list = listOf(1,2,3,4).map { it.toString() }.toList()
        assertEquals("[[], [1], [2], [3], [4], [1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4], [1, 2, 3], [1, 2, 4], [1, 3, 4], [2, 3, 4], [1, 2, 3, 4]]",
            combinationsAll(list).toString())
    }

    @Test
    fun combinationsAllWithoutExtremesTest() {
        val list = listOf(1,2,3,4).map { it.toString() }.toList()
        assertEquals("[[1], [2], [3], [4], [1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4], [1, 2, 3], [1, 2, 4], [1, 3, 4], [2, 3, 4]]",
            combinationsAllWithoutExtremes(list).toString())
    }
}