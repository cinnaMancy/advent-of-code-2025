package day9

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

//  Note: Part 2 takes like 15-20 minutes to run, but it works so fuck it we ball.
class RedTiles(
    private val tiles: List<Tile>
) {
    private val edges: List<Rectangle> =
        (tiles + tiles.first()).zipWithNext { first, second -> Rectangle(first, second) }

    private val possibleRectangles: List<Rectangle> =
        tiles.flatMapIndexed { firstIndex, first ->
            tiles.subList(firstIndex + 1, tiles.size).map { second -> Rectangle(first, second) }
        }

    fun maxRectangleArea(): Long = possibleRectangles.maxOf { it.area }

    fun maxFilledRectangleArea(): Long = possibleRectangles
        .sortedByDescending { it.area }
        .first { rectangle -> edges.none { line -> rectangle.inside()?.intersects(line) ?: false } }
        .area

    companion object {
        fun parse(lines: List<String>): RedTiles = RedTiles(lines.map { Tile.parse(it) })
    }
}

class Tile(
    val x: Long,
    val y: Long
) {
    companion object {
        fun parse(line: String): Tile = line.split(",").let { Tile(it[0].toLong(), it[1].toLong()) }
    }
}

class Rectangle(
    private val firstCorner: Tile,
    private val secondCorner: Tile
) {
    val width get(): Long = abs(firstCorner.x - secondCorner.x) + 1
    val height get(): Long = abs(firstCorner.y - secondCorner.y) + 1
    val widthIndices get(): LongRange = min(firstCorner.x, secondCorner.x)..max(firstCorner.x, secondCorner.x)
    val heightIndices get(): LongRange = min(firstCorner.y, secondCorner.y)..max(firstCorner.y, secondCorner.y)
    val area get(): Long = width * height

    fun inside(): Rectangle? {
        if (height < 3 || width < 3) return null
        val xDirectionNorm = (secondCorner.x - firstCorner.x).sign
        val yDirectionNorm = (secondCorner.y - firstCorner.y).sign
        return Rectangle(
            Tile(firstCorner.x + xDirectionNorm, firstCorner.y + yDirectionNorm),
            Tile(secondCorner.x - xDirectionNorm, secondCorner.y - yDirectionNorm)
        )
    }

    fun intersects(other: Rectangle): Boolean =
        widthIndices.last >= other.widthIndices.first
                && widthIndices.first <= other.widthIndices.last
                && heightIndices.last >= other.heightIndices.first
                && heightIndices.first <= other.heightIndices.last
}
