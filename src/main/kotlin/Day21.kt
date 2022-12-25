import javax.script.ScriptEngineManager
import javax.script.SimpleBindings

class Day21 {
    data class ActionMonkey(val dataSources:List<String>,
                      val operation:String,
                        val originalDefinition:String)

    fun linesToMonkeys(lines: List<String>):Pair<MutableMap<String,Long>,MutableMap<String,ActionMonkey>>{
        val sourceMonkeys = mutableMapOf<String,Long>()
        val actionMonkeys = mutableMapOf<String,ActionMonkey>()
        lines.forEach {
            val name = it.substringBefore(": ")
            val action = it.substringAfter(": ")
            val tokens = action.split(" ")
            if(tokens[0][0].isDigit()){
                sourceMonkeys[name]=tokens[0].toLong()
            } else {
                val sources = listOf(tokens[0],tokens[2])
                val op = tokens[1]
                val m = ActionMonkey(sources, op, action)
                actionMonkeys[name]=m
            }
        }
        return Pair(sourceMonkeys,actionMonkeys)
    }

    fun process(sourceMonkeys:MutableMap<String,Long>,actionMonkeys:MutableMap<String,ActionMonkey>) {
        while(actionMonkeys.isNotEmpty()) {
            val iterator = actionMonkeys.iterator();
            while(iterator.hasNext()){
                val e = iterator.next()
                val srcs = e.value.dataSources
                val dataExists = srcs.all {
                    sourceMonkeys.contains(it)
                }
                if(dataExists){
                    val op = e.value.operation
                    iterator.remove()
                    val newV = srcs.map{
                        sourceMonkeys[it]!!
                    }.reduce{ a,b->
                        op.toLongOp()(a,b)
                    }
                    sourceMonkeys[e.key]=newV
                }
            }
        }

    }

    fun processWithoutHumn(sourceMonkeys:MutableMap<String,Long>,
                           actionMonkeys:MutableMap<String,ActionMonkey>): Long {
        sourceMonkeys.remove("humn")
        val rootMonkey = actionMonkeys.remove("root")!!
        var oldSourceMonkeySize:Int
        do{
            oldSourceMonkeySize = sourceMonkeys.size
            val iterator = actionMonkeys.iterator();
            while(iterator.hasNext()){
                val e = iterator.next()
                val srcs = e.value.dataSources
                val dataExists = srcs.all {
                    sourceMonkeys.contains(it)
                }
                if(dataExists){
                    val op = e.value.operation
                    iterator.remove()
                    val newV = srcs.map{
                        sourceMonkeys[it]!!
                    }.reduce{ a,b->
                        op.toLongOp()(a,b)
                    }
                    sourceMonkeys[e.key]=newV
                }
            }
        } while(sourceMonkeys.size!=oldSourceMonkeySize)

        //---------
        //---------
        //---------
        var valueTowardHumn = rootMonkey.dataSources.mapNotNull{sourceMonkeys[it]}.first()
        var curKey = rootMonkey.dataSources.first{actionMonkeys.containsKey(it)}

        // EXPLOSION
        while(curKey!="humn"){
            val curMonkey = actionMonkeys[curKey]!!
            val srcs = curMonkey.dataSources
            val op = curMonkey.operation
            val (constOperandNumber,constValueFromSrcs) = srcs.mapIndexedNotNull{ i,it->
                if(sourceMonkeys.containsKey(it)) Pair(i,sourceMonkeys[it]!!) else null }
                .first()

            curKey=srcs.first{!sourceMonkeys.containsKey(it)}

            if(constOperandNumber==0) {
                println("${constValueFromSrcs} $op $curKey = $valueTowardHumn")
            } else {
                println("$curKey $op $constValueFromSrcs = $valueTowardHumn")
            }
            valueTowardHumn = revertAndApplyArithmeticOperation(op,constOperandNumber, constValueFromSrcs, valueTowardHumn)
        }

        return valueTowardHumn
    }

    fun revertAndApplyArithmeticOperation(op:String,
                                          constantOperandNumber:Int,
                                          constantOperand:Long,
                                          valueAfterEqualSign:Long):Long{

        val out = when(op){
            "*" ->{
                valueAfterEqualSign / constantOperand
            }
            "+" ->{
                valueAfterEqualSign - constantOperand
            }
            "/"->{
                if(constantOperandNumber==0){
                    // 10/x=2
                    // x=10/2
                    // valueFromSrcs/x=valueTowardHumn
                    constantOperand/valueAfterEqualSign
                } else {
                    // x/2=5
                    // x=5*2
                    valueAfterEqualSign*constantOperand
                }
            }
            "-"->{
                if(constantOperandNumber==0){
                    // 10-x=2
                    // x=10-2
                    constantOperand - valueAfterEqualSign
                } else {
                    // x-10=2
                    // x=10+2
                    valueAfterEqualSign + constantOperand
                }
            }
            else -> error("unknown operation: $op")
        }
        return out
    }

    fun partOneSolution(lines: List<String>): Long {
        val pair = linesToMonkeys(lines)
        val sourceMonkeys = pair.first
        process(sourceMonkeys, pair.second)
        return sourceMonkeys["root"]!!
    }

    fun searchValueForHumn(sourceMonkeys:MutableMap<String,Long>,
                           actionMonkeys:MutableMap<String,ActionMonkey>):Long {
        val rootMonkey = actionMonkeys.remove("root")!!
        for(i in 100_000..1_000_000) {
            val localSource = sourceMonkeys.toMutableMap()
            val localActions = actionMonkeys.toMutableMap()
            localSource["humn"] = i.toLong()
            process(localSource, localActions)
            val rootMonkeyInput = rootMonkey.dataSources.map { localSource[it] }
            if (rootMonkeyInput[0] == rootMonkeyInput[1]) {
                return i.toLong() // good
            }
        }
        error("not found")
    }

    fun linesToFormulas(lines: List<String>): MutableCollection<String> {
        val map = lines.map { Pair(it.substringBefore(":"),it.substringAfter(": ")) }.toMap().toMutableMap()
        map.remove("humn")
        val rootSources = map.remove("root")!!.split(" . ".toRegex()).toSet()
        var mapIterator = map.iterator()
        while(mapIterator.hasNext()){
            val e = mapIterator.next()
            if (e.value[0].isDigit()) {
                // iterate over all values and replace
                map.forEach {
                    map[it.key]=it.value.replace(e.key, e.value)
                }
                mapIterator.remove()
            }
        }

        mapIterator = map.iterator()
        while(mapIterator.hasNext()) {
            val e = mapIterator.next()
            if(e.key !in rootSources) {
                map.forEach {
                    map[it.key] = it.value.replace(e.key, "(" + e.value + ")")
                }
                mapIterator.remove()
            }
        }

        return map.values
    }

    fun processFormulas(formulas:List<String>): Long {
        assert(formulas.size==2)
        val engine = ScriptEngineManager().getEngineByExtension("kts")!!
        val hasVariable = formulas.filter { it.contains("humn") }.first()
        println(hasVariable)
        val hasNoVariable = formulas.filter { !it.contains("humn") }
            .map{
                engine.eval(it).toString().toLong()
            }.first()
        println(hasNoVariable)

        for(i in 0..1_000_000) {
            engine.eval(hasVariable, )
            val evaluated = engine.eval(hasVariable, SimpleBindings(mapOf(Pair("humn",i))))
            val number = evaluated.toString().toLong()
            if(number==hasNoVariable){
                return i.toLong()
            } else {
                println("value $i doesn't match")
            }
        }
        error("not found")
    }

    fun partTwoSolution(lines: List<String>): Long {
        val (sourceMonkeys,actionMonkeys) = linesToMonkeys(lines)
        val out = processWithoutHumn(sourceMonkeys,actionMonkeys)
        return out
    }

}