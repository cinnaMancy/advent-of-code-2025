package day7

import util.Grid
import util.Tile

class TachyonManifold(
    private val initialGrid: Grid
) {
    fun totalSplits(): Int {
        val finalGrid = (initialGrid.dimensions.second - 1 downTo 0)
            .fold(initialGrid) { grid, y -> progressRow(grid, y) }
        return countSplits(finalGrid)
    }

    fun totalTimelines(): Int {

    }

    private fun progressRow(grid: Grid, y: Int): Grid {
        val tilesInRow = (0..<grid.dimensions.first).map { x -> grid[x, y]!! }
        val nextTilesInRow = tilesInRow.map { tile ->
            if (tile.isEmpty() && beamContinues(grid, tile)) Tile(tile.coordinates, '|')
            else tile
        }
        return grid.replace(nextTilesInRow)
    }

    private fun countSplits(grid: Grid): Int = grid.tiles.count { tile ->
        tile.isSplitter() && grid[tile.x, tile.y + 1].isEmitting()
    }

    private fun beamContinues(grid: Grid, tile: Tile): Boolean {
        val continuesStraight = tile.above(grid).isEmitting()
        val continuesSplit = tile.sides(grid).any { side -> side.isSplitter() && grid[side.x, side.y + 1].isEmitting() }
        return continuesStraight || continuesSplit
    }

    private fun Tile.above(grid: Grid): Tile? = grid[x, y + 1]

    private fun Tile.sides(grid: Grid): List<Tile> = listOfNotNull(grid[x - 1, y], grid[x + 1, y])

    private fun Tile?.isEmpty(): Boolean = this?.content == '.'

    private fun Tile?.isSplitter(): Boolean = this?.content == '^'

    private fun Tile?.isEmitting(): Boolean = this?.content == '|' || this?.content == 'S'

    companion object {
        fun parse(lines: List<String>): TachyonManifold = TachyonManifold(Grid.parse(lines))
    }
}
