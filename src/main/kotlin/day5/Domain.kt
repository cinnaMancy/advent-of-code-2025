package day5

class FreshIds(
    private val freshIdRanges: List<LongRange>,
    private val ids: List<Long>
) {
    fun freshIdsCount() = ids.count { id -> freshIdRanges.any { range -> range.contains(id) } }

    fun allPossibleFreshIdsCount(): Long = freshIdRanges
        .flatMapIndexed { index, range -> range.withoutAll(freshIdRanges.subList(0, index)) }
        .sumOf { it.last - it.first + 1 }

    private fun LongRange.withoutAll(previous: List<LongRange>): List<LongRange> = previous
        .fold(listOf(this)) { acc, next -> acc.flatMap { it.without(next) } }

    private fun LongRange.without(other: LongRange): List<LongRange> {
        if (first > other.last || last < other.first) {
            //  Doesn't intersect
            return listOf(this)
        } else if (first >= other.first && last <= other.last) {
            //  Fully overlaps
            return emptyList()
        } else if (first < other.first && last > other.last) {
            //  Intersects in the middle
            return listOf(first..(other.first - 1), (other.last + 1)..last)
        } else {
            //  Intersects partially
            if (first >= other.first) {
                return listOf((other.last + 1)..last)
            } else {
                return listOf(first..(other.first - 1))
            }
        }
    }

    companion object {
        fun parse(lines: List<String>): FreshIds = FreshIds(
            lines.takeWhile { it.isNotEmpty() }.map { it.split('-') }.map { it[0].toLong()..it[1].toLong() },
            lines.takeLastWhile { it.isNotEmpty() }.map { it.toLong() }
        )
    }
}
