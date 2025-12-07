package util

class Grid(
    val tiles: List<Tile>
) {
    val dimensions get(): Pair<Int, Int> = Pair(tiles.maxOf { it.x } + 1, tiles.maxOf { it.y } + 1)

    val rowIndices get(): IntRange = 0..<dimensions.second

    val columnIndices get(): IntRange = 0..<dimensions.first

    operator fun get(coordinates: Coordinates): Tile? = tiles.find { it.coordinates == coordinates }

    operator fun get(x: Int, y: Int): Tile? = get(Coordinates(x, y))

    fun adjacent(tile: Tile): List<Tile> =
        (-1..1).flatMap { dx -> (-1..1).map { dy -> Coordinates(tile.x + dx, tile.y + dy) } }
            .filterNot { it.x == tile.x && it.y == tile.y }
            .mapNotNull { get(it) }

    fun directlyAdjacent(tile: Tile): List<Tile> = adjacent(tile)
        .filter { it.x == tile.x || it.y == tile.y }

    fun row(y: Int): List<Tile> = (0..<dimensions.first).mapNotNull { x -> this[x, y] }

    fun replace(replaced: List<Tile>): Grid = Grid(
        tiles.map { tile -> replaced.find { oneReplaced -> tile.coordinates == oneReplaced.coordinates } ?: tile }
    )

    override fun toString(): String =
        (dimensions.second - 1 downTo 0).joinToString("\n") { y ->
            (0..<dimensions.first).joinToString("") { x ->
                this[Coordinates(x, y)]!!.content.toString()
            }
        }

    companion object {
        fun parse(lines: List<String>): Grid =
            Grid(lines.reversed().flatMapIndexed { iY, y -> y.mapIndexed { iX, x -> Tile(Coordinates(iX, iY), x) } })
    }
}

data class Tile(
    val coordinates: Coordinates,
    val content: Char
) {
    val x: Int get() = coordinates.x
    val y: Int get() = coordinates.y
}

data class Coordinates(
    val x: Int,
    val y: Int
) {
    operator fun times(scalar: Int): Coordinates = Coordinates(x * scalar, y * scalar)

    operator fun Int.times(coordinates: Coordinates) = coordinates * this

    operator fun plus(other: Coordinates): Coordinates = Coordinates(x + other.x, y + other.y)

    operator fun minus(other: Coordinates): Coordinates = this + (-1 * other)
}
