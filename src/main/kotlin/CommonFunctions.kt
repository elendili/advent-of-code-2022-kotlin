import java.util.*

fun getResourceAsLines(path: String): List<String> =
    getResourceAsText(path).lines()

fun getResourceAsText(path: String): String =
    object {}.javaClass.getResource(path).readText()


fun list2dToString(a: List<List<Any>>): String {
    return a.joinToString("\n") { it.joinToString("") }
}

fun array2dToString(a: Array<Array<*>>): String {
    return a.joinToString("\n") { it.joinToString("") }
}

fun linesToMatrix(lines: List<String>): List<List<Int>> = lines.map { line ->
    line.map { it.digitToInt() }
}

/*
/* arr[]  ---> Input Array
    data[] ---> Temporary array to store current combination
    start & end ---> Starting and Ending indexes in arr[]
    index  ---> Current index in data[]
    r ---> Size of a combination to be printed */
    static void combinationUtil(int arr[], int n, int r, int index,
                                int data[], int i)
    {
        // Current combination is ready to be printed, print it
        if (index == r)
        {
            for (int j=0; j<r; j++)
                System.out.print(data[j]+" ");
            System.out.println("");
        return;
        }

        // When no more elements are there to put in data[]
        if (i >= n)
        return;

        // current is included, put next at next location
        data[index] = arr[i];
        combinationUtil(arr, n, r, index+1, data, i+1);

        // current is excluded, replace it with next (Note that
        // i+1 is passed, but index is not changed)
        combinationUtil(arr, n, r, index, data, i+1);
    }

    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    static void printCombination(int arr[], int n, int r)
    {
        // A temporary array to store all combination one by one
        int data[]=new int[r];

        // Print all combination using temporary array 'data[]'
        combinationUtil(arr, n, r, 0, data, 0);

 */
fun combinationsAll(list: List<String>):MutableList<List<String>>{
    val out: MutableList<List<String>> = mutableListOf()
    for(i in 0..list.size){
        out.addAll(combinationsOfSize(list,i))
    }
    return out
}
fun combinationsAllWithoutExtremes(list: List<String>):MutableList<List<String>>{
    val out: MutableList<List<String>> = mutableListOf()
    for(i in 1..list.lastIndex){
        out.addAll(combinationsOfSize(list,i))
    }
    return out
}
fun combinationsOfSize(list: List<String>, r:Int):MutableList<List<String>>{
    val out: MutableList<List<String>> = mutableListOf()
    combinationsOfSize(list, Array(r){""}, r, 0, 0, out);
    return out
}

fun combinationsOfSize(
    list: List<String>,
    data: Array<String>,
    r: Int,
    index:Int,
    i: Int,
    out: MutableList<List<String>>
) {
    if (index == r) {
        out.add(data.map { it!! }.toList())
        return
    }
    if(i>=list.size){ return }
    data[index]=list[i]
    combinationsOfSize(list, data, r, index+1, i+1, out);
    combinationsOfSize(list, data, r, index, i+1, out);

}

fun isDebug(): Boolean = false

inline fun whenDebug(action: () -> Unit) {
    if (isDebug()) {
        action()
    }
}