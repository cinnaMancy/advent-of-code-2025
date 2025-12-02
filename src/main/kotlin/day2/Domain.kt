package day2

class InvalidIdRanges(
    private val elements: List<InvalidIdRange>
) {
    fun twoPartSum(): Long = elements
        .sumOf { it.invalidWholeSum() }

    fun anyPartsSum(): Long = elements
        .sumOf { it.invalidPartSum() }

    companion object {
        fun parse(wholeLine: String): InvalidIdRanges = InvalidIdRanges(
            wholeLine.split(",").map { InvalidIdRange.parse(it) }
        )
    }
}

class InvalidIdRange(
    private val start: Long,
    private val end: Long
) {
    fun invalidWholeSum(): Long = invalidSum("^(\\d+)\\1\$".toRegex())

    fun invalidPartSum(): Long = invalidSum("^(\\d+)\\1+\$".toRegex())

    private fun invalidSum(regex: Regex): Long = (start..end)
        .filter { it.toString().matches(regex) }
        .sumOf { it }

    companion object {
        fun parse(line: String): InvalidIdRange = line
            .split("-")
            .let { InvalidIdRange(it[0].toLong(), it[1].toLong()) }
    }
}