package day4

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input = """
        ..@@.@@@@.
        @@@.@.@.@@
        @@@@@.@.@@
        @.@@@@..@.
        @@.@@@@.@@
        .@@@@@@@.@
        .@.@.@.@@@
        @.@@@.@@@@
        .@@@@@@@@.
        @.@.@@@.@.
    """.trimIndent().lines()

    @Test
    fun part1Example() {
        val paperGrid = PaperGrid.parse(input)
        val accessibleRollsCount = paperGrid.accessibleRollsCount()
        assertEquals(13, accessibleRollsCount)
    }
}