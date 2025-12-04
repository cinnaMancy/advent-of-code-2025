package util

class Board(
    val tiles: List<Tile>
) {
    val dimensions get(): Pair<Int, Int> = Pair(tiles.maxOf { it.coordinates.x }, tiles.maxOf { it.coordinates.y })

    operator fun get(coordinates: Coordinates): Tile? =
        tiles.find { it.coordinates == coordinates }

    fun adjacent(tile: Tile): List<Tile> =
        (-1..1).flatMap { dx -> (-1..1).map { dy -> Coordinates(tile.coordinates.x + dx, tile.coordinates.y + dy) } }
            .filterNot { it.x == tile.coordinates.x && it.y == tile.coordinates.y }
            .mapNotNull { get(it) }

    fun directlyAdjacent(tile: Tile): List<Tile> = adjacent(tile)
        .filter { it.coordinates.x == tile.coordinates.x || it.coordinates.y == tile.coordinates.y }

    companion object {
        fun parse(lines: List<String>): Board =
            Board(lines.reversed().flatMapIndexed { iY, y -> y.mapIndexed { iX, x -> Tile(Coordinates(iX, iY), x) } })
    }
}

data class Tile(
    val coordinates: Coordinates,
    val content: Char
)

data class Coordinates(
    val x: Int,
    val y: Int
) {
    operator fun times(scalar: Int): Coordinates = Coordinates(x * scalar, y * scalar)

    operator fun Int.times(coordinates: Coordinates) = coordinates * this

    operator fun plus(other: Coordinates): Coordinates = Coordinates(x + other.x, y + other.y)

    operator fun minus(other: Coordinates): Coordinates = this + (-1 * other)
}
