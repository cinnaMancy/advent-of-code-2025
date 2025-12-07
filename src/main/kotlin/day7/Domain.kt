package day7

import util.Grid
import util.Tile

class TachyonManifold(
    private val initialGrid: Grid
) {
    fun totalSplits(): Int {
        val finalGrid = rowIndicesTopToBottomExceptTop().fold(initialGrid) { grid, y -> progressRow(grid, y) }
        return countSplits(finalGrid)
    }

    fun totalTimelines(): Long {
        val startTile = CountedBeam(initialGrid.tiles.first { it.isEmitting() }, 1L)
        return rowIndicesTopToBottomExceptTop().fold(listOf(startTile)) { beams, y ->
            beams.flatMap { beam -> beamsTo(beam, initialGrid) }
                //  Concatenate multiple counted beams on the same tile into a single one
                .groupBy { beam -> beam.tile.coordinates }
                .mapValues { (_, beams) -> beams.sumOf { it.count } }
                .map { (coordinates, beamCount) -> CountedBeam(Tile(coordinates, '|'), beamCount) }
        }.sumOf { it.count }
    }

    private fun beamsTo(beam: CountedBeam, grid: Grid): List<CountedBeam> {
        val below = beam.tile.below(grid)
        if (below.isEmpty()) {
            return listOf(CountedBeam(below!!, beam.count))
        }
        return below?.sides(grid)?.map { side -> CountedBeam(side, beam.count) }!!
    }

    private fun rowIndicesTopToBottomExceptTop(): IntProgression =
        initialGrid.rowIndices.last - 1 downTo initialGrid.rowIndices.first

    private fun progressRow(grid: Grid, y: Int): Grid = grid.replace(progressible(grid, grid.row(y)))

    private fun progressible(grid: Grid, tiles: List<Tile>): List<Tile> = tiles
        .filter { it.isEmpty() && beamContinues(grid, it) }
        .map { Tile(it.coordinates, '|') }

    private fun countSplits(grid: Grid): Int = grid.tiles.count { tile ->
        tile.isSplitter() && grid[tile.x, tile.y + 1].isEmitting()
    }

    private fun beamContinues(grid: Grid, tile: Tile): Boolean {
        val continuesStraight = tile.above(grid).isEmitting()
        val continuesSplit = tile.sides(grid).any { side -> side.isSplitter() && grid[side.x, side.y + 1].isEmitting() }
        return continuesStraight || continuesSplit
    }

    private fun Tile.above(grid: Grid): Tile? = grid[x, y + 1]

    private fun Tile.below(grid: Grid): Tile? = grid[x, y - 1]

    private fun Tile.sides(grid: Grid): List<Tile> = listOfNotNull(grid[x - 1, y], grid[x + 1, y])

    private fun Tile?.isEmpty(): Boolean = this?.content == '.'

    private fun Tile?.isSplitter(): Boolean = this?.content == '^'

    private fun Tile?.isEmitting(): Boolean = this?.content == 'S' || this?.content == '|'

    companion object {
        fun parse(lines: List<String>): TachyonManifold = TachyonManifold(Grid.parse(lines))
    }
}

data class CountedBeam(
    val tile: Tile,
    val count: Long
)
