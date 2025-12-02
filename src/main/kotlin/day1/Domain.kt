package day1

class RotationSequence(
    private val rotations: List<Rotation>
) {
    fun endClicks(): Int = rotations
        .runningFold(50L) { position, rotation -> rotate(position, rotation) }
        .count { it % 100 == 0L }

    fun allClicks(): Int = rotations
        .runningFold(50L) { position, rotation -> rotate(position, rotation) }
        .zipWithNext()
        .sumOf { clicksAmount(it.first, it.second) }

    private fun rotate(position: Long, rotation: Rotation): Long =
        position + rotation.displacement

    private fun clicksAmount(firstPosition: Long, secondPosition: Long): Int =
        if (firstPosition >= secondPosition) clicksGoingUpZeroAmount(firstPosition, secondPosition)
        else clicksGoingUpZeroAmount(-firstPosition, -secondPosition)

    private fun clicksGoingUpZeroAmount(firstPosition: Long, secondPosition: Long): Int =
        (firstPosition.floorDiv(100) - secondPosition.floorDiv(100)).toInt()

    companion object {
        fun parse(lines: List<String>) = RotationSequence(lines.map(Rotation.Companion::parse))
    }
}

class Rotation(
    private val direction: Direction,
    private val distance: Long
) {
    val displacement get(): Long = direction.distanceMultiplier * distance

    companion object {
        fun parse(line: String) = Rotation(
            Direction.parse(line.first()),
            line.substring(1).toLong()
        )
    }
}

enum class Direction(
    val distanceMultiplier: Long
) {
    LEFT(-1),
    RIGHT(1);

    companion object {
        fun parse(letter: Char): Direction =
            entries.first { it.name.startsWith(letter) }
    }
}