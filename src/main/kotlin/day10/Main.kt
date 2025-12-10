package day10

import util.readTextLines

fun main() {
    val input = readTextLines("/day10.txt")
    val machines = Machines.parse(input)
    println("Part 1: The fewest button presses required to turn on all machines is ${machines.fewestTotalPresses()}.")
}