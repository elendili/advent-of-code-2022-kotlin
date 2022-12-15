import kotlin.math.absoluteValue

class Day15 {
    data class SensorsAndBeacons(
        val sensors: Set<PointYX>,
        val beacons: Set<PointYX>,
        val sensorsToClosestBeacons: Map<PointYX, PointYX>,
        val sensorsToClosestBeaconDistance: Map<PointYX, Int>
    )


    fun linesToSensorsAndBeacons(lines: List<String>): SensorsAndBeacons {
        val sensors = mutableSetOf<PointYX>()
        val beacons = mutableSetOf<PointYX>()
        val sensorsToClosestBeacons = mutableMapOf<PointYX, PointYX>()
        val sensorsToClosestBeaconDistance = mutableMapOf<PointYX, Int>()
        lines.forEach {
            val numbers = it.splitIgnoreEmpty("[^0-9-]".toRegex()).map { it.toInt() }
            val sensor = PointYX(numbers[1], numbers[0])
            val beacon = PointYX(numbers[3], numbers[2])
            sensors.add(sensor)
            beacons.add(beacon)
            sensorsToClosestBeacons[sensor] = beacon
            sensorsToClosestBeaconDistance[sensor] = sensor.distanceTo(beacon)
        }
        return SensorsAndBeacons(sensors, beacons, sensorsToClosestBeacons, sensorsToClosestBeaconDistance)
    }

    fun pointIsCoveredBySensor(p:PointYX, sensors:Set<PointYX>,
                               sensorsToClosestBeaconDistance: Map<PointYX, Int>):PointYX?{
        for (sensor in sensors) {
            val distanceToSensor = p.distanceTo(sensor)
            val distanceCoveredBySensor = sensorsToClosestBeaconDistance[sensor]!!
            val diff = distanceToSensor - distanceCoveredBySensor
            if (diff <= 0) {
                return sensor
            }
        }
        return null
    }

    fun partOneSolution(lines: List<String>, row: Int): Int {
        val data = linesToSensorsAndBeacons(lines)
        val biggestDistance = data.sensorsToClosestBeaconDistance.values.max()
        val gp = getGridPropertiesFromPointYXs(data.sensors + data.beacons)

        val leftRowPoint = gp.minX - biggestDistance
        val rightRowPoint = gp.maxX + biggestDistance
        // count covered points
        var coveredPointsOnARow = 0
        for (x in leftRowPoint..rightRowPoint) {
            if(pointIsCoveredBySensor(
                    PointYX(row,x),
                    data.sensors,
                    data.sensorsToClosestBeaconDistance
                )!=null){
                coveredPointsOnARow++
            }
        }
        // count points with beacon or sensor in a row
        val occupiedPoints = data.beacons.count { it.y == row } + data.sensors.count { it.y == row }

        return coveredPointsOnARow - occupiedPoints
    }

    fun getUncoveredPoint(data:SensorsAndBeacons, coordinateLimit: Int):PointYX{
        for(y in 0..coordinateLimit){
            var x = 0
            while(x<=coordinateLimit){
                val p = PointYX(y,x)
                val sensor = pointIsCoveredBySensor(p,data.sensors,data.sensorsToClosestBeaconDistance)
                if (sensor==null){
                    // no sensor coverage here
                    return p
                } else {
                    // search next X where given sensor has no coverage
                    val distToBeacon = data.sensorsToClosestBeaconDistance[sensor]!!
                    val distToRow = (sensor.y - y).absoluteValue
                    val lastCoveredX = sensor.x + (distToBeacon - distToRow)
                    x = lastCoveredX+1
                }
            }
        }
        error("No Uncovered Point")
    }

    fun partTwoSolution(lines: List<String>, coordinateLimit: Int): Long {
        val data = linesToSensorsAndBeacons(lines)
        val p = getUncoveredPoint(data,coordinateLimit)
        val out = p.x*4000000L+p.y
        return out
    }

}