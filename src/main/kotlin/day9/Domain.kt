package day9

import kotlin.math.abs

class RedTiles(
    private val tiles: List<RedTile>
) {
    fun maxRectangleArea(): Long {
        val allRectangles = tiles.flatMapIndexed { firstIndex, first ->
            tiles.subList(firstIndex + 1, tiles.size).map { second ->
                RedRectangle(first, second)
            }
        }
        return allRectangles.maxOf { it.area }
    }

    companion object {
        fun parse(lines: List<String>): RedTiles = RedTiles(lines.map { RedTile.parse(it) })
    }
}

class RedTile(
    val x: Long,
    val y: Long
) {
    companion object {
        fun parse(line: String): RedTile = line.split(",").let { RedTile(it[0].toLong(), it[1].toLong()) }
    }
}

class RedRectangle(
    private val firstCorner: RedTile,
    private val secondCorner: RedTile
) {
    val area get(): Long = (abs(firstCorner.x - secondCorner.x) + 1) * (abs(firstCorner.y - secondCorner.y) + 1)
}