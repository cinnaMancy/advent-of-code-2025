package day2

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"

    @Test
    fun part1Example() {
        val invalidIdRanges = InvalidIdRanges.parse(input)
        val twoPartSum = invalidIdRanges.twoPartSum()
        assertEquals(1227775554, twoPartSum)
    }

    @Test
    fun part2Example() {
        val invalidIdRanges = InvalidIdRanges.parse(input)
        val anyPartsSum = invalidIdRanges.anyPartsSum()
        assertEquals(4174379265, anyPartsSum)
    }
}