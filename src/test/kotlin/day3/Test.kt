package day3

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input = """
        987654321111111
        811111111111119
        234234234234278
        818181911112111
    """.trimIndent().lines()

    @Test
    fun part1Example() {
        val batteryBanks = BatteryBanks.parse(input)
        val totalMaximumJoltage = batteryBanks.totalMaximumShortJoltage()
        assertEquals(357, totalMaximumJoltage)
    }
}