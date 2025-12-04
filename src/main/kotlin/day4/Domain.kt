package day4

import util.Board
import util.Tile

class PaperGrid(
    private val grid: Board
) {
    //  TODO: Refactor Board!! (i need at least filter(), but otherwise make it behave like a collection)
    fun accessibleRollsCount(): Int = accessibleRolls().count()

//    fun recursivelyAccessibleRollsCount(): Int {
//        val currentlyAccessible = accessibleRolls()
//        if (currentlyAccessible.isEmpty()) return 0
//        currentlyAccessible
//    }

    private fun accessibleRolls(): List<Tile> = grid.tiles.filter { tile ->
        tile.content == '@' && grid.adjacent(tile).count { adjacent -> adjacent.content == '@' } < 4
    }

    companion object {
        fun parse(lines: List<String>): PaperGrid = PaperGrid(Board.parse(lines))
    }
}