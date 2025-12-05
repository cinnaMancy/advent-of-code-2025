package day5

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input = """
        3-5
        10-14
        16-20
        12-18

        1
        5
        8
        11
        17
        32
    """.trimIndent().lines()

    @Test
    fun part1Example() {
        val freshIds = FreshIds.parse(input)
        val freshIdsCount = freshIds.freshIdsCount()
        assertEquals(3, freshIdsCount)
    }

    @Test
    fun part2Example() {
        val freshIds = FreshIds.parse(input)
        val allPossibleFreshIdsCount = freshIds.allPossibleFreshIdsCount()
        assertEquals(14, allPossibleFreshIdsCount)
    }
}
