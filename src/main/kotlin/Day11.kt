
class Day11 {
    class Monkey(
        val id: Int,
        val items: MutableList<Long> = mutableListOf(),
        val operation: (Long) -> Long,
        val divisorForTest: Long,
        val monkeyTrue: Int,
        val monkeyFalse: Int,
        val monkeys: List<Monkey>,
        val manageWorryLevel: (Long) -> Long,
    ) {
        var inspectsCount: Long = 0
        fun inspectItem(worryLevel: Long) {
            val worryLevelAfterOperation = operation(worryLevel)
            val worryLevelAfterBoring = manageWorryLevel(worryLevelAfterOperation)
            val targetMonkey = if (worryLevelAfterBoring.mod(divisorForTest)==0L) monkeyTrue else monkeyFalse
            monkeys[targetMonkey].items.add(worryLevelAfterBoring)
            inspectsCount = inspectsCount.inc()
        }

        fun inspectItems() {
            items.forEach { inspectItem(it) }
            items.clear()
        }

        override fun toString(): String {
            return "Monkey(id=$id, items=$items, " +
                    "operation=$operation, divisorForTest=$divisorForTest, " +
                    "monkeyTrue=$monkeyTrue, monkeyFalse=$monkeyFalse, " +
                    "inspectsCount=$inspectsCount)"
        }

    }

    fun linesToLcm(lines: List<String>): Long {
        val out = lines.filter { it.contains("Test: divisible by ") }
            .map { it.substringAfter("Test: divisible by ").toInt() }
            .reduce{i,j->i*j}
        return out.toLong()
    }

    fun linesToMonkeys(lines: List<String>, manageWorryLevel:(Long)->Long): List<Monkey> {
        val monkeys = mutableListOf<Monkey>()
        var items: MutableList<Long> = mutableListOf()
        var operation: (Long) -> Long = Long::dec
        var divisorForTest = 1L
        var monkeyTrue = 0
        var monkeyFalse = 0
        var id = 0
        lines.forEach {
            when {
                it.isEmpty() -> {}
                it.startsWith("Monkey ") -> {}
                it.contains("Starting items: ") -> {
                    items = it.substringAfter("Starting items: ")
                        .split(", ").map{it.toLong()}.toMutableList()
                }

                it.contains("Operation: new = old ") -> {
                    val operationString = it.substringAfter("Operation: new = old ")
                    val (o, r) = operationString.split(" ")
                    operation = when (r) {
                        "old" -> {
                            { v -> o.toLongOp()(v, v) }
                        }

                        else -> {
                            { v -> o.toLongOp()(v, r.toLong()) }
                        }
                    }
                }

                it.contains("Test: divisible by ") -> {
                    divisorForTest = it.substringAfter("Test: divisible by ").toLong()
                }

                it.contains("If true: throw to monkey ") -> {
                    val m = it.substringAfter("If true: throw to monkey ").toInt()
                    monkeyTrue = m
                }

                it.contains("If false: throw to monkey ") -> {
                    val m = it.substringAfter("If false: throw to monkey ").toInt()
                    monkeyFalse = m

                    // flush data to new Monkey
                    val mo = Monkey(id++,items, operation, divisorForTest,
                        monkeyTrue, monkeyFalse, monkeys, manageWorryLevel)
                    assert( mo.id==monkeys.size ) {"Monkey id mismatches size of monkey collection"}
                    monkeys.add(mo)
                }

                else -> error("Unrecognized string '$it'")
            }
        }
        whenDebug {
            monkeys.forEach{
                println(it)
                println()
            }
            println("-----------------")
        }


        return monkeys
    }

    fun get2ActiveMonkeysInspectCountsMultiplied(monkeys:List<Monkey>):Long{
        return monkeys
            .map { it.inspectsCount }
            .sorted().takeLast(2)
            .reduceRight{i,j->i*j}
    }

    fun repeatMonkeysBusiness(monkeys:List<Monkey>, count:Int){
        repeat(count) { round->
            monkeys.forEach { it.inspectItems() }
            whenDebug {
                println("Round $round")
                monkeys.forEachIndexed { i, it ->
                    println("Monkey ${i}: ${it.items}")
                }
                println()
            }
        }
    }

    fun partOneSolution(lines: List<String>): Long {
        val lcm = linesToLcm(lines)
        val monkeys = linesToMonkeys(lines) { v -> v.floorDiv(3).mod(lcm) }

        repeatMonkeysBusiness(monkeys,20)
        return get2ActiveMonkeysInspectCountsMultiplied(monkeys)
    }

    fun partTwoSolution(lines: List<String>): Long {
        val lcm = linesToLcm(lines)
        val monkeys = linesToMonkeys(lines){ v -> v.mod(lcm) }
        repeatMonkeysBusiness(monkeys,10000)
        return get2ActiveMonkeysInspectCountsMultiplied(monkeys)
    }


}