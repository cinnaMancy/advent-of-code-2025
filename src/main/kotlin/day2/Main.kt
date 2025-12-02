package day2

import util.readText

fun main() {
    val input = readText("/day2.txt")
    val invalidIdRanges = InvalidIdRanges.parse(input)
    println("Part 1: The sum of invalid two-part IDs is ${invalidIdRanges.twoPartSum()}.")
    println("Part 2: The sum of invalid any-parts IDs is ${invalidIdRanges.anyPartsSum()}.")
}