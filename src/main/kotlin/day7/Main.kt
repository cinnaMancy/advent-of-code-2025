package day7

import util.readTextLines

fun main() {
    val input = readTextLines("/day7.txt")
    val tachyonManifold = TachyonManifold.parse(input)
    println("Part 1: The number of times the beam is split is ${tachyonManifold.totalSplits()}.")
}