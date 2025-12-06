package day6

import util.readTextLines

fun main() {
    val input = readTextLines("/day6.txt")
    val mathProblems = MathProblems.parse(input)
    println("Part 1: The grand total of all answers to the math problems is ${mathProblems.total()}")
    val cephalidMathProblems = MathProblems.parseCephalopod(input)
    println("Part 2: The grand total of all answers to the cephalopod-corrected math problems is ${cephalidMathProblems.total()}")
}