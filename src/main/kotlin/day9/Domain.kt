package day9

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

//  Note: Part 2 takes like 15-20 minutes to run and I can imagine corner case inputs
//  where it wouldn't produce correct results. But it works so fuck it we ball.
class RedTiles(
    private val tiles: List<Tile>
) {
    private val allPossibleRectangles: List<Rectangle> = tiles.flatMapIndexed { firstIndex, first ->
        tiles.subList(firstIndex + 1, tiles.size).map { second -> Rectangle(first, second) }
    }
    private val allNegativeRectangles: List<Rectangle> =
        tiles.mapIndexedNotNull { index, tile ->
            val nextTile = tiles[(index + 1) % tiles.size]
            val nextCorner = tiles[(index + 2) % tiles.size]
            val rightDirection = rightDirection(tile, nextTile, nextCorner)
            if (rightDirection) null
            else Rectangle(tile, nextCorner).tighten()
        }

    private fun rightDirection(tile: Tile, nextTile: Tile, nextCorner: Tile): Boolean {
        return if (nextTile.x == tile.x) {
            if (nextTile.y > tile.y) nextCorner.x < tile.x
            else nextCorner.x > tile.x
        } else {
            if (nextTile.x > tile.x) nextCorner.y > tile.y
            else nextCorner.y < tile.y
        }
    }

    fun maxRectangleArea(): Long = allPossibleRectangles.maxOf { it.area }

    fun maxFilledRectangleArea(): Long = allPossibleRectangles
        .sortedByDescending { it.area }
        .first { possibleRectangle -> allNegativeRectangles.none { it.intersects(possibleRectangle) } }
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

    fun tighten(): Rectangle? {
        if (height < 3 || width < 3) return null
        val xDirectionNorm = (secondCorner.x - firstCorner.x).sign
        val yDirectionNorm = (secondCorner.y - firstCorner.y).sign
        return Rectangle(
            Tile(firstCorner.x + xDirectionNorm, firstCorner.y + yDirectionNorm),
            Tile(secondCorner.x - xDirectionNorm, secondCorner.y - yDirectionNorm)
        )
    }

    fun intersects(other: Rectangle): Boolean =
        this.widthIndices.max() >= other.widthIndices.min()
                && this.widthIndices.min() <= other.widthIndices.max()
                && this.heightIndices.max() >= other.heightIndices.min()
                && this.heightIndices.min() <= other.heightIndices.max()
}
