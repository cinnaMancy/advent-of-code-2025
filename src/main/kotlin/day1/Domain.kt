package day1

class RotationSequence(
    private val rotations: List<Rotation>
) {
    fun endClicks(): Int = rotations
        .runningFold(50) { position, rotation -> rotate(position, rotation) }
        .count { it == 0 }

    fun allClicks(): Int = rotations
        .fold(Pair(50, 0)) { positionToAllClicks, rotation ->
            Pair(
                rotate(positionToAllClicks.first, rotation),
                positionToAllClicks.second + passesZeroAmount(positionToAllClicks.first, rotation)
            )
        }.second

    private fun rotate(position: Int, rotation: Rotation): Int {
        var newPosition = (position + rotation.displacement) % 100
        if (newPosition < 0) newPosition += 100
        return newPosition
    }

    private fun passesZeroAmount(position: Int, rotation: Rotation): Int {
        val unclippedNewPosition = position + rotation.displacement
        return if (unclippedNewPosition > 0) {
            unclippedNewPosition / 100
        } else {
            val firstPassover = if (position == 0) 0 else 1
            (-unclippedNewPosition / 100) + firstPassover
        }
    }

    companion object {
        fun parse(lines: List<String>) = RotationSequence(lines.map(Rotation.Companion::parse))
    }
}

class Rotation(
    private val direction: Direction,
    private val distance: Int
) {
    val displacement get(): Int = direction.distanceMultiplier * distance

    companion object {
        fun parse(line: String) = Rotation(
            Direction.parse(line.first()),
            line.substring(1).toInt()
        )
    }
}

enum class Direction(
    val distanceMultiplier: Int
) {
    LEFT(-1),
    RIGHT(1);

    companion object {
        fun parse(letter: Char): Direction =
            entries.first { it.name.startsWith(letter) }
    }
}