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

    private fun progressRow(grid: Grid, y: Int): Grid {
        val tilesInRow = (0..<grid.dimensions.first).map { x -> grid[x, y]!! }
        val nextTilesInRow = tilesInRow.map { tile ->
            if (tile.content == '.' && beamContinues(grid, tile)) Tile(tile.coordinates, '|')
            else tile
        }
        return grid.replace(nextTilesInRow)
    }

    private fun countSplits(grid: Grid): Int = grid.tiles.count { tile ->
        tile.content == '^' && grid[tile.x, tile.y + 1]?.content == '|'
    }

    private fun beamContinues(grid: Grid, tile: Tile): Boolean {
        val above = grid[tile.x, tile.y + 1]
        val continuesStraight = above?.content == '|' || above?.content == 'S'
        val sides = listOf(grid[tile.x - 1, tile.y], grid[tile.x + 1, tile.y])
        val continuesSplit = sides.any { side -> side?.content == '^' && grid[side.x, side.y + 1]?.content == '|' }
        return continuesStraight || continuesSplit
    }

    companion object {
        fun parse(lines: List<String>): TachyonManifold = TachyonManifold(Grid.parse(lines))
    }
}
