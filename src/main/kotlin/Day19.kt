import Day19.Resource.*
import java.lang.Integer.max

class Day19 {
    enum class Resource{
        ore,clay,obsidian,geode, no
    }
    data class RobotCosts(val resourcesForARobot:Map<Resource,Int>)

    data class Blueprint(val robotCosts:Map<Resource,RobotCosts>){


        val maxResourceRequirements = getRoboMaxResourceRequirements()

        private fun getRoboMaxResourceRequirements(): Map<Resource, Int> {
            val out:MutableMap<Resource,Int> = mutableMapOf()
            robotCosts.forEach { (roboType,roboCosts) ->
                roboCosts.resourcesForARobot.forEach{(resType,amount)->
                    out[resType]=max(out.getOrDefault(resType,0),amount)
                }
            }
            return out
        }

        fun maxGeodsCanBeOpenedByBlueprint(minutes:Int):Int{
            val roboInventory: MutableMap<Resource, Int> = mutableMapOf(Pair(ore,1))
            val resourceInventory: MutableMap<Resource, Int> = mutableMapOf<Resource,Int>()

            val out = recursion(minutes,
                roboInventory,
                resourceInventory
            )

            return out
        }

        var maxOfGeods = 0
        fun recursion(minutes:Int,
                      roboInventory: Map<Resource, Int>,
                      resourceInventory: Map<Resource, Int>
        ):Int{
            // optimize by dropping branches where
            // impossible to get more geods than maximum
            val curGeodes = resourceInventory.getOrDefault(geode,0)
            val maxPossibleGeodsInBranch =
                curGeodes + (0 until minutes).sum() +
                        minutes*roboInventory.getOrDefault(geode,0)

            //choose
            if(minutes==0 || maxPossibleGeodsInBranch<=maxOfGeods){
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
                        roboCost.resourcesForARobot.all { (res, amount) ->
                            resourceInventory.getOrDefault(res, 0) >= amount
                        }
                    }

                val variations : MutableMap<Resource,RobotCosts?> = buildNowRobots.toMutableMap()

                if(buildNowRobots.size<robotCosts.size
                    || geode !in buildNowRobots
                ) {
                    variations[no] = null
                }

                // iterate over different choices
                val out = variations.map { (actionType, robotCosts) ->

                    val localResourceInventory = resourceInventory.toMutableMap()
                    val localRoboInventory = roboInventory.toMutableMap()

                    // spend resources for robot
                    if(actionType!= no) {
                        this.robotCosts[actionType]!!.resourcesForARobot.forEach { (res, amount) ->
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
                    if(actionType!= no) {
                        localRoboInventory.compute(actionType) { _, robotsCount ->
                            if (robotsCount == null) 1 else robotsCount + 1
                        }
                    }

                    recursion(minutes-1,
                        localRoboInventory,
                        localResourceInventory
                    )
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

        var robotsCosts: MutableMap<Resource,RobotCosts> = mutableMapOf()
        val preOut = mutableListOf<MutableMap<Resource,RobotCosts>>()
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
                robotsCosts[robotType]=RobotCosts(roboCostsMap);
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
        val geodsOpenedByBlueprint = bs.map{ b ->
            b.maxGeodsCanBeOpenedByBlueprint(32)
        }
        val multipliedGeodsCounts = geodsOpenedByBlueprint.reduce{a,b->a*b}
        return multipliedGeodsCounts
    }


}