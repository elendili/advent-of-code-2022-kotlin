import java.lang.Integer.max
import java.util.*
import kotlin.collections.LinkedHashSet

class Day16 {

    data class Valve(val name:String, val flowRate:Int,
                     val tunnelsTo:LinkedHashSet<String> = LinkedHashSet())

    class Valves{
        val mapValves : Map<String,Valve>
        val valvesToOpen : Set<String>
        val pathsFromValveToValvesMap: Map<String, Map<String,Int>>
        constructor(lines: List<String>){
            mapValves = linesToData(lines)
            valvesToOpen = mapValves.filterValues { it.flowRate>0 }.keys
            pathsFromValveToValvesMap = getPathsMap()
        }

        private fun linesToData(lines: List<String>):Map<String,Valve>{
            val out = mutableMapOf<String,Valve>()
            val valveToTunnels = mutableMapOf<String,Set<String>>()
            lines.forEach {
                // Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
                val name = it.substringAfter("Valve ").substringBefore(" has flow rate")
                val flowRate = it.substringAfter("rate=").substringBefore("; tunnel").toInt()
                val tunnelsTo = it.substringAfter("to valve").splitIgnoreEmpty("[^A-Z]".toRegex()).toSet()
                out[name] = Valve(name, flowRate)
                valveToTunnels[name]=tunnelsTo
            }
            valveToTunnels.forEach{ (k, v) ->
                out[k]!!.tunnelsTo.addAll( v.sortedBy { out[it]!!.flowRate }.reversed() )
            }
            return out
        }

        fun getPathsMap(): Map<String, Map<String,Int>> {
            return mapValves.keys.associateWith {
                val localValves = valvesToOpen.toMutableSet()
                localValves.remove(it)
                getPathsLengthsToValves(it, localValves)
            }
        }

        fun getPathsLengthsToValves(originValve:String, valves:Set<String>):Map<String,Int>{
            val visited = mutableSetOf(originValve)
            val q = LinkedList<String>()
            q.add(originValve)
            val out = mutableMapOf<String,Int>()
            var level=0
            while(q.isNotEmpty()){
                var levelSize = q.size
                while(levelSize-- > 0){
                    val c = q.poll()
                    if(c in valves){
                        out[c]=level
                    }
                    mapValves[c]!!.tunnelsTo
                        .filter{visited.add(it)}
                        .forEach{ q.add(it)}
                }
                level++
            }
            return out
        }

        fun getMaxPossibleReleaseForMinutesRecursive(minutes:Int):Int{
            return getMaxPossibleReleaseForMinutesRecursive(minutes,"AA", valvesToOpen)
        }

        // comes to still closed valve
        fun getMaxPossibleReleaseForMinutesRecursive(minutes:Int,valve:String,valvesToOpen:Set<String> ):Int{
            var ifCurrentOpened = 0
            whenDebug {
                println("a. minutes=$minutes . valve=$valve . toOpen=$valvesToOpen")
            }
            if(valvesToOpen.contains(valve)) {
//                calc overall benefit if open current
                val localValvesToOpen = valvesToOpen.toMutableSet()
                localValvesToOpen.remove(valve)

                val localMinutes = minutes - 1
                val valveBenefitIfOpenNow = localMinutes * mapValves[valve]!!.flowRate

                var benefitsFromNextValvesMax = 0
                if (localValvesToOpen.isNotEmpty()) {
                    val paths = pathsFromValveToValvesMap[valve]!!
                                    .filterKeys { valvesToOpen.contains(it) }
                    val benefitsFromNextValves = paths.map { (vk, path) ->

                        val valveMinutes = localMinutes - path
                        if(valveMinutes>0) {
                            whenDebug {
                                println("b. valveMinutes=$valveMinutes . valve=$vk . path=$path")
                            }
                            val b = getMaxPossibleReleaseForMinutesRecursive(valveMinutes, vk, localValvesToOpen)
                            b
                        } else 0
                    }
                    benefitsFromNextValvesMax = benefitsFromNextValves.maxOrNull() ?: 0
                }

                ifCurrentOpened = valveBenefitIfOpenNow + benefitsFromNextValvesMax
            }

//          calc overall benefit if current is closed
            var ifCurrentClosed = 0
            if(valvesToOpen.isNotEmpty() && !valvesToOpen.contains(valve)) {
                val paths = pathsFromValveToValvesMap[valve]!!
                                .filterKeys { valvesToOpen.contains(it) }
                val benefitsFromNextValves = paths.map { (vk, path) ->
                    val valveMinutes = minutes - path
                    if(valveMinutes>0) {
                        whenDebug {
                            println("c. valveMinutes=$valveMinutes . valve=$vk . path=$path")
                        }
                        val b = getMaxPossibleReleaseForMinutesRecursive(valveMinutes, vk, valvesToOpen)
                        b
                    } else 0
                }
                ifCurrentClosed = benefitsFromNextValves.maxOrNull() ?: 0
            }

            val out = max(ifCurrentOpened,ifCurrentClosed)
            return out
        }

        fun getPathsBenefitLengthsToValvesToOpen(minutes:Int,valvePaths:Map<String,Int>):Map<String,Int>{
            val out = mutableMapOf<String,Int>()
            valvePaths.map {(valve,path)->
                val timeOfOpenValve = minutes - (path + 1) // 1 is for opening valve
                val flowRate = mapValves[valve]!!.flowRate
                out[valve] = flowRate * timeOfOpenValve
            }
            return out
        }



    }

    /**
     * new approach:
     * find the shortest path from entrance to all valves with flowRate > 0
     * calc weights of achieving every valve like this:
     *  (left minutes - path length)*(flow rate) - that how much can be achieved if choose this valve to open
     * "run" to this valve, open it.
     * find the shortest weights to remaining valves
     * calc weights of achieving every valve (left minutes are less now), choose the best, "run" to it
     */



    fun partOneSolution(lines: List<String>): Int {
        val vs = Valves(lines)
        val out = vs.getMaxPossibleReleaseForMinutesRecursive(30,"AA",vs.valvesToOpen)
        return out
    }

    fun partTwoSolution(lines: List<String>): Int {
        val vs = Valves(lines)
        val valvesToOpen = vs.valvesToOpen

        val combinations = combinationsAllWithoutExtremes(valvesToOpen.toList()).map{it.toSet()}

        val countsOfCombinations = combinations.map{
            val out1 = vs.getMaxPossibleReleaseForMinutesRecursive(26,"AA",it)
            val out2 = vs.getMaxPossibleReleaseForMinutesRecursive(26,"AA",valvesToOpen.subtract(it))
            out1+out2
        }
        val max = countsOfCombinations.max()
        return max
    }

}