package day1

import util.readTextLines

fun main() {
    val input = readTextLines("/day1.txt")
    val rotationSequence = RotationSequence.parse(input)
    println("Part 1: The number of time the dial is pointing at 0 after a rotation is ${rotationSequence.endClicks()}.")
    println("Part 2: The number of time the dial passes 0 after during rotations is ${rotationSequence.allClicks()}.")
}