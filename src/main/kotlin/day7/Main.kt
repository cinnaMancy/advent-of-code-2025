package day7

import util.readTextLines

fun main() {
    val input = readTextLines("/day7.txt")
    val tachyonManifold = TachyonManifold.parse(input)
    println("Part 1: The number of times the beam is split is ${tachyonManifold.totalSplits()}.")
    println("Part 2: The number of timelines the particle travels is ${tachyonManifold.totalTimelines()}.")
}