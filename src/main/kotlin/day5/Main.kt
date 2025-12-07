package day5

import util.readTextLines

fun main() {
    val input = readTextLines("/day5.txt")
    val freshIds = FreshIds.parse(input)
    println("Part 1: The number of fresh ids is ${freshIds.freshIdsCount()}.")
    println("Part 2: The number of all possible fresh ids is ${freshIds.allPossibleFreshIdsCount()}.")
}
