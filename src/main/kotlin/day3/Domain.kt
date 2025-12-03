package day3

class BatteryBanks(private val banks: List<BatteryBank>) {
    fun totalMaximumShortJoltage(): Long = banks.sumOf { it.maximumShortJoltage() }

    fun totalMaximumLongJoltage(): Long = banks.sumOf { it.maximumLongJoltage() }

    companion object {
        fun parse(lines: List<String>): BatteryBanks = BatteryBanks(lines.map { BatteryBank.parse(it) })
    }
}

class BatteryBank(private val batteries: List<Battery>) {
    fun maximumShortJoltage(): Long = maximumJoltage(batteries, 2)

    fun maximumLongJoltage(): Long = maximumJoltage(batteries, 12)

    private fun maximumJoltage(elements: List<Battery>, remainingDigits: Int): Long {
        val currentDigit = elements.dropLast(remainingDigits - 1).maxOf { it.joltage }
        if (remainingDigits == 1) {
            return currentDigit.toLong()
        }
        val firstCurrentDigitIndex = elements.indexOfFirst { it.joltage == currentDigit }
        val nextDigit = maximumJoltage(
            elements.drop(firstCurrentDigitIndex + 1),
            remainingDigits - 1
        )
        return "$currentDigit$nextDigit".toLong()
    }

    companion object {
        fun parse(line: String): BatteryBank = BatteryBank(line.map { Battery.parse(it) })
    }
}

class Battery(
    val joltage: Int
) {
    companion object {
        fun parse(char: Char): Battery = Battery(char.digitToInt())
    }
}