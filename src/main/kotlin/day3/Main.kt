package day3

import util.readTextLines

fun main() {
    val input = readTextLines("/day3.txt")
    val batteryBanks = BatteryBanks.parse(input)
    println("Part 1: The total maximum short joltage of the battery banks is ${batteryBanks.totalMaximumShortJoltage()}.")
    println("Part 1: The total maximum long joltage of the battery banks is ${batteryBanks.totalMaximumLongJoltage()}.")
}