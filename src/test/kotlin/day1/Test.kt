package day1

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input = """
        L68
        L30
        R48
        L5
        R60
        L55
        L1
        L99
        R14
        L82
    """.trimIndent().lines()

    @Test
    fun part1Example() {
        val rotationSequence = RotationSequence.parse(input)
        val endClicks = rotationSequence.endClicks()
        assertEquals(3, endClicks)
    }

    @Test
    fun part2Example() {
        val rotationSequence = RotationSequence.parse(input)
        val allClicks = rotationSequence.allClicks()
        assertEquals(6, allClicks)
    }
}