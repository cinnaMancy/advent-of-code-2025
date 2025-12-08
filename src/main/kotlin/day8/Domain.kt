package day8

class JunctionBoxes(
    private val boxes: List<JunctionBox>
) {
    private val allPairsByDistanceAscending: List<JunctionBoxPair> =
        boxes.flatMapIndexed { firstIndex, first ->
            boxes.subList(firstIndex + 1, boxes.size).map { second ->
                JunctionBoxPair(first, second)
            }
        }.sortedBy { it.squaredDistance }

    fun productOf3LargestGroupsAfterConnecting(connections: Int): Long = connect(connections).groups
        .sortedByDescending { it.size }
        .take(3)
        .fold(1) { acc, coords -> acc * coords.size }

    fun productOfXCoordinatesOfLastConnection(): Long =
        generateSequence(JunctionBoxesConfiguration.initial(boxes)) { config ->
            if (config.groups.size == 1) null else connectTwoClosesGroups(config)
        }.last()
            .connections
            .last()
            .let { it.first.x * it.second.x }


    private fun connect(connections: Int): JunctionBoxesConfiguration =
        (0..<connections).fold(JunctionBoxesConfiguration.initial(boxes)) { acc, _ -> connectTwoClosesGroups(acc) }

    private fun connectTwoClosesGroups(config: JunctionBoxesConfiguration): JunctionBoxesConfiguration {
        val closestUnconnectedPair = allPairsByDistanceAscending.first { !config.connections.contains(it) }
        val firstGroup = config.groups.first { it.contains(closestUnconnectedPair.first) }
        val secondGroup = config.groups.first { it.contains(closestUnconnectedPair.second) }
        val newGroups =
            if (firstGroup != secondGroup) config.groups
                .minus(setOf(firstGroup, secondGroup))
                .plus(listOf(firstGroup.plus(secondGroup)))
            else config.groups
        return JunctionBoxesConfiguration(newGroups, config.connections.plus(closestUnconnectedPair))
    }

    companion object {
        fun parse(lines: List<String>): JunctionBoxes = JunctionBoxes(lines.map(JunctionBox.Companion::parse))
    }
}

class JunctionBox(
    val x: Long,
    val y: Long,
    val z: Long
) {
    fun squaredDistance(other: JunctionBox): Long =
        ((x - other.x) * (x - other.x)) + ((y - other.y) * (y - other.y)) + ((z - other.z) * (z - other.z))

    companion object {
        fun parse(line: String): JunctionBox = line
            .split(",")
            .let { JunctionBox(it[0].toLong(), it[1].toLong(), it[2].toLong()) }
    }
}

class JunctionBoxPair(
    val first: JunctionBox,
    val second: JunctionBox
) {
    val squaredDistance: Long = first.squaredDistance(second)
}

class JunctionBoxesConfiguration(
    val groups: List<List<JunctionBox>>,
    val connections: List<JunctionBoxPair>
) {
    companion object {
        fun initial(boxes: List<JunctionBox>): JunctionBoxesConfiguration = JunctionBoxesConfiguration(
            boxes.map { listOf(it) },
            emptyList()
        )
    }
}