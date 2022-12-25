import Day19.Resource.*
import java.lang.Integer.max

class Day19 {
    enum class Resource{
        ore,clay,obsidian,geode, no
    }
    data class Blueprint(val robotCosts:Map<Resource,Map<Resource,Int>>){
        val maxResourceRequirements = getRoboMaxResourceRequirements()

        private fun getRoboMaxResourceRequirements(): Map<Resource, Int> {
            val out:MutableMap<Resource,Int> = mutableMapOf()
            robotCosts.forEach { (roboType,roboCosts) ->
                roboCosts.forEach{(resType,amount)->
                    out[resType]=max(out.getOrDefault(resType,0),amount)
                }
            }
            return out
        }

        fun maxGeodsCanBeOpenedByBlueprint(minutes:Int):Int{
            val roboInventory: MutableMap<Resource, Int> = mutableMapOf(Pair(ore,1))
            val resourceInventory: MutableMap<Resource, Int> = mutableMapOf<Resource,Int>()
            minutesTarget = minutes

            val out = if(false){
                everyStepRecursion(0,
                    roboInventory,
                    resourceInventory,
                    listOf()
                )
            } else {
                jumpRecursion(0,
                    roboInventory,
                    resourceInventory,
                    listOf()
                )
            }

            return out
        }

        var maxOfGeods = 0
        var minutesTarget = 0
        fun everyStepRecursion(
                      currentMinutes:Int,
                      roboInventory: Map<Resource, Int>,
                      resourceInventory: Map<Resource, Int>,
                      log:List<String>
        ):Int{
            // optimize by dropping branches where
            // impossible to get more geods than maximum
            val curGeodes = resourceInventory.getOrDefault(geode,0)
            val leftMinutes = minutesTarget - currentMinutes
            val maxPossibleGeodsInBranch =
                curGeodes + (0 until leftMinutes).sum() +
                        leftMinutes*roboInventory.getOrDefault(geode,0)

            //choose
            if(currentMinutes>=minutesTarget || maxPossibleGeodsInBranch<=maxOfGeods){
                maxOfGeods = max(maxOfGeods, curGeodes)
                return curGeodes
            } else {

                val notEnoughRobotsAndResources = Resource.values().filter {
                    val maxRes = maxResourceRequirements.getOrDefault(it,0)
                    (roboInventory.getOrDefault(it,0) <= maxRes &&
                            resourceInventory.getOrDefault(it,0) <=  maxRes)
                            || it==geode
                }

                val buildNowRobots = this.robotCosts
                    .filter {
                        it.key in notEnoughRobotsAndResources
                    }
                    .filter { (roboType,roboCost) ->
                        roboCost.all { (res, amount) ->
                            resourceInventory.getOrDefault(res, 0) >= amount
                        }
                    }

                val variations : MutableMap<Resource,Map<Resource,Int>?> = buildNowRobots.toMutableMap()

                if(buildNowRobots.size<robotCosts.size
                    || geode !in buildNowRobots
                ) {
                    variations[no] = null
                }

                // iterate over different choices
                val out = variations.map { (buildRoboType, robotCosts) ->

                    val localResourceInventory = resourceInventory.toMutableMap()
                    val localRoboInventory = roboInventory.toMutableMap()

                    // spend resources for robot
                    if(buildRoboType!= no) {
                        this.robotCosts[buildRoboType]!!.forEach { (res, amount) ->
                            localResourceInventory[res] = localResourceInventory[res]!! - amount
                        }
                    }

                    // gather resources
                    localRoboInventory.forEach { roboResource, roboAmount ->
                        localResourceInventory.compute(roboResource) { _, resAmount ->
                            if (resAmount == null) roboAmount else resAmount + roboAmount
                        }
                    }

                    // update robo inventory
                    if(buildRoboType!= no) {
                        localRoboInventory.compute(buildRoboType) { _, robotsCount ->
                            if (robotsCount == null) 1 else robotsCount + 1
                        }
                    }

                    everyStepRecursion(
                        currentMinutes+1,
                        localRoboInventory,
                        localResourceInventory,
                        log + "$currentMinutes. $buildRoboType. robots=$localRoboInventory. resources=$localResourceInventory"
                    )
                }.max()
                return out
            }


        }

        fun jumpRecursion(
                      currentMinutes:Int,
                      roboInventory: Map<Resource, Int>,
                      resourceInventory: Map<Resource, Int>,
                      log:List<String>
        ):Int{
            // optimize by dropping branches where
            // impossible to get more geods than maximum
            val curGeodes = resourceInventory.getOrDefault(geode,0)
            val leftMinutes = minutesTarget - currentMinutes
            val maxPossibleGeodsInBranch =
                curGeodes + (0 until leftMinutes).sum() +
                        leftMinutes*roboInventory.getOrDefault(geode,0)

            //choose
            if(currentMinutes>=minutesTarget || maxPossibleGeodsInBranch<=maxOfGeods){
                maxOfGeods = max(maxOfGeods, curGeodes)
                return curGeodes
            } else {

                val notEnoughRobotsAndResources = robotCosts.filter {
                        (roboType,costMap)->
                    val maxRes = maxResourceRequirements.getOrDefault(roboType,0)
                    (roboInventory.getOrDefault(roboType,0) <= maxRes
                            && resourceInventory.getOrDefault(roboType,0) <=  maxRes*2
                            )
                }

                val buildRobots = this.robotCosts
                    .filter {
                        it.key in notEnoughRobotsAndResources || it.key==geode
                    }
                    .filter { (roboType,roboCost) ->
                        roboCost.all { (res, amount) ->
                            roboInventory.getOrDefault(res, 0) > 0
                        }
                    }

                // iterate over different choices
                val out = buildRobots.map { (buildRoboType, robotCosts) ->

                    val localResourceInventory = resourceInventory.toMutableMap()
                    val localRoboInventory = roboInventory.toMutableMap()

                    // calc required minutes to build this robot type
                    val requiredMinutes = 1 + robotCosts.map{ (res,cost) ->
                        val roboCount = localRoboInventory.getOrDefault(res,0)
                        val costToCoverByProduction = cost - localResourceInventory.getOrDefault(res,0)
                        val out = if(costToCoverByProduction>=1){
                            costToCoverByProduction.div(roboCount) + (if (costToCoverByProduction.mod(roboCount)>0) 1 else 0)
                        } else 0
                        out
                    }.max()  // +1 for building minute
                    if(requiredMinutes<=leftMinutes) {

                        // calc gathered resources during those minutes
                        localRoboInventory.forEach { roboResource, roboAmount ->
                            localResourceInventory.compute(roboResource) { _, resAmount ->
                                val delta = roboAmount * (requiredMinutes)
                                if (resAmount == null) delta else resAmount + delta
                            }
                        }

                        // spend resources for robot
                        // minus from resource inventory
                        robotCosts.forEach { (res, amount) ->
                            localResourceInventory[res] = localResourceInventory[res]!! - amount
                        }

                        // add robot to robo inventory
                        localRoboInventory.compute(buildRoboType) { _, robotsCount ->
                            if (robotsCount == null) 1 else robotsCount + 1
                        }

                        // go to recursion
                        var newMinutes = currentMinutes + requiredMinutes
                        jumpRecursion(
                            newMinutes,
                            localRoboInventory,
                            localResourceInventory,
                            log + "$newMinutes. $buildRoboType. robots=$localRoboInventory. resources=$localResourceInventory"
                        )
                    } else {
                        // calc gathered geodes during left minutes till the end
                        val geods = resourceInventory.getOrDefault(geode,0)+(roboInventory.getOrDefault(geode,0)*leftMinutes)
                        geods
                    }

                }.max()
                return out
            }


        }

    }
    fun linesToBlueprints(lines: List<String>):List<Blueprint>{
        val preparedLines = lines
            .flatMap {it.split(":")  }
            .flatMap { it.splitIgnoreEmpty("Each".toRegex()) }
            .flatMap { it.split(".") }
            .map{ it.trim().trim() }
            .filter{ it.isNotEmpty() && it.isNotBlank()  }

        var robotsCosts: MutableMap<Resource,Map<Resource,Int>> = mutableMapOf()
        val preOut = mutableListOf<MutableMap<Resource,Map<Resource,Int>>>()
        for (i in preparedLines.indices){
            val line = preparedLines[i]
            if (line.contains("Blueprint") ) {
                robotsCosts = mutableMapOf()
                preOut.add(robotsCosts)
            } else {
                val robotType = valueOf(line.substringBefore(" "))
                val roboCostsMap = line
                    .substringAfter("costs ")
                    .splitIgnoreEmpty(" and ".toRegex())
                    .associate{
                        val amount = it.substringBefore(" ").toInt()
                        val res = valueOf(it.substringAfter(" "))
                        Pair(res, amount)
                    }
                robotsCosts[robotType]=roboCostsMap;
            }
        }
        val out = preOut.map{Blueprint(it)}
        return out
    }




    fun partOneSolution(lines: List<String>): Int {
        val bs = linesToBlueprints(lines)
        val geodsOpenedByBlueprint = bs.map{ b ->
            b.maxGeodsCanBeOpenedByBlueprint(24)
        }
        val multipliedGeodsCounts = geodsOpenedByBlueprint.mapIndexed{
                i,geodsCount -> (i+1)*geodsCount
        }
        return multipliedGeodsCounts.sum()
    }

    fun partTwoSolution(lines: List<String>): Int {
        val bs = linesToBlueprints(lines)
        val geodsOpenedByBlueprint = bs.take(3)
            .map{ b ->
            b.maxGeodsCanBeOpenedByBlueprint(32)
        }
        val multipliedGeodsCounts = geodsOpenedByBlueprint.reduce{a,b->a*b}
        return multipliedGeodsCounts
    }


}