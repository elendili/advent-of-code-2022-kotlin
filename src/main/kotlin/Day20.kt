import kotlin.math.absoluteValue
import kotlin.math.sign

class Day20 {
    data class Node(val v:Long, var prev:Node?=null, var next:Node?=null) {
        var nodesCount:Int=1
        fun moveByStepsDefinedByValue() {
//            val limit = v.absoluteValue
            val limit = v.absoluteValue.mod(nodesCount-1)
            for(i in 0 until limit){
                move(v.sign)
            }
        }
        fun updateNodesCount(){
            nodesCount=getFullListFromCurrentForward().size
        }
        fun move(v:Int){
            val oldNext = next!!
            val oldPrev = prev!!
            val oldPrevPrev = oldPrev.prev!!
            val oldNextNext = oldNext.next!!

            if(v>0){ // to the right
                next=oldNextNext
                prev=oldNext
                oldPrev.next = oldNext
                oldNextNext.prev=this
                oldNext.prev=oldPrev
                oldNext.next=this
            } else { // to the left
                next=oldPrev
                prev=oldPrevPrev
                oldPrev.next = oldNext
                oldPrev.prev = this
                oldPrevPrev.next=this
                oldNext.prev = oldPrev
            }
        }

        override fun toString(): String {
            return "$v"
        }

        fun getNodeByNumberFromCurrent(i:Int): Node {
            var c = this
            for(j in 0 until i){
                c = c.next!!
            }
            return c
        }

        fun getFullListFromCurrentForward(): MutableList<Long> {
            val out = mutableListOf<Long>()
            var c = this
            do {
                out.add(c.v)
                c = c.next!!
            } while(c!=this)
            return out
        }

        fun getFullListFromCurrentBackward(): MutableList<Long> {
            val out = mutableListOf<Long>()
            var c = this
            do {
                out.add(c.v)
                c = c.prev!!
            } while(c!=this)
            return out
        }
    }

    fun connectNodesInCircle(nodes:List<Node>){
        val firstOne = nodes.first()
        val circled = (nodes+firstOne)
        circled.windowed(2)
            .forEach {
                val f = it.first()
                val l = it.last()
                if(f.next==null){
                    f.next=l
                }
                if(l.prev==null){
                    l.prev=f
                }
            }
    }

    fun partOneSolution(lines: List<String>): Long {
        val numbers = lines.mapToInts()
        val nodes = numbers.map{it.toLong()}.map { Node(it) }
        val zeroValueOne = nodes.first{ it.v == 0L}
        connectNodesInCircle(nodes)
        nodes.forEach{
            it.updateNodesCount()
        }
        nodes.forEachIndexed{ i,it->
            it.moveByStepsDefinedByValue()
//            println("$i .. $it")
        }

        val _1000 = zeroValueOne.getNodeByNumberFromCurrent(1000)
        val _2000 = _1000.getNodeByNumberFromCurrent(1000)
        val _3000 = _2000.getNodeByNumberFromCurrent(1000)
        val out = listOf(_1000,_2000,_3000).map{it.v}.sum()
        return out;
    }

    fun partTwoSolution(lines: List<String>): Long {
        val numbers = lines.mapToInts()
        val multiplied = numbers.map{it*811589153L}
        val nodes = multiplied.map { Node(it) }
        val zeroValueOne = nodes.first{ it.v == 0L}
        connectNodesInCircle(nodes)
        nodes.forEach{
            it.updateNodesCount()
        }
        repeat(10) {
            nodes.forEachIndexed { i, it ->
                it.moveByStepsDefinedByValue()
//            println("$i .. $it")
            }
        }

        val _1000 = zeroValueOne.getNodeByNumberFromCurrent(1000)
        val _2000 = _1000.getNodeByNumberFromCurrent(1000)
        val _3000 = _2000.getNodeByNumberFromCurrent(1000)
        val out = listOf(_1000,_2000,_3000).map{it.v}.sum()
        return out;
    }

}