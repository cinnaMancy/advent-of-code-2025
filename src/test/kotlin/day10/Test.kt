package day10

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input = """
        [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
        [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
        [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
    """.trimIndent().lines()

    @Test
    fun part1Example() {
        val machines = Machines.parse(input)
        val fewestTotalPresses = machines.fewestTotalPresses()
        assertEquals(7, fewestTotalPresses)
    }
}