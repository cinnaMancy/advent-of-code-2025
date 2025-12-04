package day4

import util.readTextLines

fun main() {
    val input = readTextLines("/day4.txt")
    val paperGrid = PaperGrid.parse(input)
    println("Part 1: The number of accessible rolls of papers is ${paperGrid.accessibleRollsCount()}.")
    println("Part 2: The number of recursively accessible rolls of papers is ${paperGrid.recursivelyAccessibleRollsCount()}.")
}