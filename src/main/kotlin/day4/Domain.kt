package day4

import util.Grid
import util.Tile

class PaperGrid(
    private val grid: Grid
) {
    fun accessibleRollsCount(): Int = accessibleRolls().count()

    fun recursivelyAccessibleRollsCount(): Int {
        val currentlyAccessible = accessibleRolls()
        if (currentlyAccessible.isEmpty()) return 0
        val nextTiles =
            grid.tiles.map { Tile(it.coordinates, if (currentlyAccessible.contains(it)) '.' else it.content) }
        return currentlyAccessible.count() + PaperGrid(Grid(nextTiles)).recursivelyAccessibleRollsCount()
    }

    private fun accessibleRolls(): List<Tile> = grid.tiles.filter(::isAccessible)

    private fun isAccessible(tile: Tile): Boolean =
        tile.content == '@' && grid.adjacent(tile).count { adjacent -> adjacent.content == '@' } < 4

    companion object {
        fun parse(lines: List<String>): PaperGrid = PaperGrid(Grid.parse(lines))
    }
}