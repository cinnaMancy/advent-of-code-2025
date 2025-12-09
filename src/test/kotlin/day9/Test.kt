package day9

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input = """
        7,1
        11,1
        11,7
        9,7
        9,5
        2,5
        2,3
        7,3
    """.trimIndent().lines()

    @Test
    fun part1Example() {
        val redTiles = RedTiles.parse(input)
        val maxRectangleArea = redTiles.maxRectangleArea()
        assertEquals(50, maxRectangleArea)
    }

    @Test
    fun part2Example() {
        val redTiles = RedTiles.parse(input)
        val maxFilledRectangleArea = redTiles.maxFilledRectangleArea()
        assertEquals(24, maxFilledRectangleArea)
    }
}