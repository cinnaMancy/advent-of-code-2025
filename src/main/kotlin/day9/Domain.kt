package day9

import util.Coordinates
import util.Grid
import util.Tile
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class RedTiles(
    private val grid: Grid
) {
    private val redTiles: List<Tile> = grid.tiles.filter { it.content == '#' }

    fun maxRectangleArea(): Long =
        allPossibleRectangles().maxOf { (firstCorner, secondCorner) -> area(firstCorner, secondCorner) }

    fun maxFilledRectangleArea(): Long = allPossibleRectangles()
        .filter { (firstCorner, secondCorner) -> grid.rectangle(firstCorner, secondCorner).none { it.content == '.' } }
        .maxOf { (firstCorner, secondCorner) -> area(firstCorner, secondCorner) }

    private fun allPossibleRectangles(): List<Pair<Tile, Tile>> =
        redTiles.flatMapIndexed { firstIndex, first ->
            redTiles.subList(firstIndex + 1, redTiles.size).map { second ->
                Pair(first, second)
            }
        }

    private fun area(firstCorner: Tile, otherCorner: Tile): Long =
        (abs(firstCorner.x - otherCorner.x) + 1L) * (abs(firstCorner.y - otherCorner.y) + 1L)

    companion object {
        fun parse(lines: List<String>): RedTiles {
            val allCoordinates = lines.map { it.split(",") }.map { Coordinates(it[0].toInt(), it[1].toInt()) }
            val dimensions = Pair(allCoordinates.maxOf { it.x + 2 }, allCoordinates.maxOf { it.y + 2 })
            val redTiles = allCoordinates.map { Tile(it, '#') }
            val redLines = redTiles.zipWithNext().plus(Pair(redTiles.last(), redTiles.first()))
            val connectingGreenTiles = redLines
                .flatMap { (first, second) -> lineCoordinatesBetween(first, second) }
                .map { coordinates -> Tile(coordinates, 'X') }
            val emptyTiles = (0..<dimensions.first).flatMap { x ->
                (0..<dimensions.second).map { y ->
                    Tile(Coordinates(x, y), '.')
                }
            }
            val linesGrid = Grid(emptyTiles).replace(redTiles).replace(connectingGreenTiles)
                .also { println("~~~ LinesGrid: ~~~\n$it\n~~~") }
            val floodedGrid =
                generateSequence(FloodingGrid(linesGrid, listOf(aTileInside(linesGrid)))) { (grid, flooded) ->
                    flood(grid, flooded)
                }.last().grid.also { println("~~~ FloodedGrid: ~~~\n$it\n~~~") }
            return RedTiles(floodedGrid)
        }

        private fun lineCoordinatesBetween(first: Tile, second: Tile): List<Coordinates> =
            if (first.y == second.y) (min(first.x, second.x) + 1..max(first.x, second.x) - 1)
                .map { Coordinates(it, first.y) }
            else (min(first.y, second.y) + 1..max(first.y, second.y) - 1)
                .map { Coordinates(first.x, it) }

        //  Note: There's a change this fails on some grids, but it's very low so fuck it we ball
        private fun aTileInside(linesGrid: Grid) =
            linesGrid.tiles
                .groupBy { it.y }
                .map { it.value }
                .map { rowTiles -> rowTiles.filter { it.content == 'X' } }
                .first { it.count() == 2 && abs(it[0].x - it[1].x) != 1 }
                .let { Coordinates(min(it[0].x, it[1].x) + 1, it[0].y) }
                .let { Tile(it, 'X') }

        private fun flood(grid: Grid, flooded: List<Tile>): FloodingGrid? {
            val toFlood = flooded
                .flatMap { grid.directlyAdjacent(it) }
                .distinct()
                .filter { it.content == '.' }
                .map { Tile(it.coordinates, 'X') }
            return if (toFlood.isEmpty()) null else FloodingGrid(grid.replace(flooded), flooded.plus(toFlood))

        }
    }
}

data class FloodingGrid(
    val grid: Grid,
    val flooded: List<Tile>
)