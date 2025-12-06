package day6

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input = """
        123 328  51 64 
         45 64  387 23 
          6 98  215 314
        *   +   *   +  
    """.trimIndent().lines()

    @Test
    fun part1Example() {
        val mathProblems = MathProblems.parse(input)
        val total = mathProblems.total()
        assertEquals(4277556, total)
    }

    @Test
    fun part2Example() {
        val mathProblems = MathProblems.parseCephalopod(input)
        val total = mathProblems.total()
        assertEquals(3263827, total)
    }
}