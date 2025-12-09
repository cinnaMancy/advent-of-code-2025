package day9

import util.readTextLines

fun main() {
    val input = readTextLines("/day9.txt")
    val redTiles = RedTiles.parse(input)
    println("Part 1: The largest possible area of a rectangle is ${redTiles.maxRectangleArea()}.")
    println("Part 2: The largest possible area of a filled rectangle is ${redTiles.maxFilledRectangleArea()}.")
}