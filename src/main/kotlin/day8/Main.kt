package day8

import util.readTextLines

fun main() {
    val input = readTextLines("/day8.txt")
    val junctionBoxes = JunctionBoxes.parse(input)
    println(
        "Part 1: The product of the three largest groups' sizes after connecting 1000 junction boxes is ${
            junctionBoxes.productOf3LargestGroupsAfterConnecting(1000)
        }."
    )
    println(
        "Part 2: The product of X coordinates of the two last connecting junction boxes ${
            junctionBoxes.productOfXCoordinatesOfLastConnection()
        }."
    )
}