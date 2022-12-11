
class Day10 {
    fun execute(log:MutableList<Int>, command:String){
        log+=log.last()
        whenDebug {
            println("${log.size}. ${log.last()}")
        }
        if (command.startsWith("addx")) {
            val delta = command.substringAfter(" ").toInt()
            val newRegisterValue = log.last()+delta
            log+=newRegisterValue
            whenDebug {
                println("${log.size}. ${log.last()}")
            }
        }
    }

    fun linesToRegisterValuesByCycles(lines: List<String>):List<Int>{
        val registerValuesLog = mutableListOf(1,1)
        lines.forEach{
            execute(registerValuesLog, it)
        }
        return registerValuesLog
    }

    fun partOneSolution(lines: List<String>): Int {
        val registerValuesLog = linesToRegisterValuesByCycles(lines)
        val cyclesToCheck = listOf(20, 60, 100, 140, 180, 220)
        whenDebug {
            println(" ------------------------- ")
        }
        val registerValuesAtSpecificCycles = cyclesToCheck.map{
            val regValueOnCycle = registerValuesLog[it]
            val out = it*regValueOnCycle
            whenDebug {
                println("$it*$regValueOnCycle=$out")
            }
            out
        }
        val out = registerValuesAtSpecificCycles.sum()
        return out
    }

    fun partTwoSolution(lines: List<String>): String {
        val registerValuesLog = linesToRegisterValuesByCycles(lines)
        val display= mutableListOf<Char>()
        registerValuesLog.drop(1).take(240)
            .forEachIndexed{ i,regVal->
            val px = i.mod(40)
            if (px in regVal-1..regVal+1){
                display.add('#')
            } else {
                display.add('.')
            }
        }
        val out =
            display.chunked(40).map{ it.joinToString("")}.joinToString("\n")
        return out
    }


}