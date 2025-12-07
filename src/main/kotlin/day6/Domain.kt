package day6

import kotlin.math.min

class MathProblems(
    private val problems: List<MathProblem>
) {
    fun total(): Long = problems.sumOf(MathProblem::solve)

    companion object {
        fun parse(lines: List<String>): MathProblems = MathProblems(
            parseColumns(lines).map { MathProblem.parse(it) }
        )

        fun parseCephalopod(lines: List<String>): MathProblems = MathProblems(
            parseColumns(lines).map(::toCephalopodColumn).map { MathProblem.parse(it) }
        )

        private fun parseColumns(lines: List<String>): List<List<String>> {
            val emptyColumnIndices = lines[0].indices.filter { index ->
                lines.all { line -> line[index].isWhitespace() }
            }
            val spacefulRows = lines.map { line ->
                (listOf(-1) + emptyColumnIndices + lines[0].length).zipWithNext().map { indexPair ->
                    line.substring(indexPair.first + 1..<min(indexPair.second, line.length))
                }
            }
            val spacefulColumns =
                spacefulRows[0].indices.map { columnIndex -> spacefulRows.map { row -> row[columnIndex] } }
            return spacefulColumns
        }

        private fun toCephalopodColumn(column: List<String>): List<String> {
            val numbers = column.dropLast(1)
            val width = numbers.maxOf { it.length }
            val cephalidNumbers = (0..<width).map { columnIndex ->
                numbers.indices.map { rowIndex ->
                    numbers[rowIndex][columnIndex]
                }.joinToString("")
            }
            return cephalidNumbers + column.last()
        }
    }
}

class MathProblem(
    private val operands: List<Long>,
    private val operator: Operator
) {
    fun solve() = operands.reduce { acc, next -> operator.apply(acc, next) }

    companion object {
        fun parse(column: List<String>) = MathProblem(
            column.dropLast(1).map { it.removeWhitespace() }.map(String::toLong),
            Operator.parse(column.last().removeWhitespace()[0])
        )

        private fun String.removeWhitespace(): String = replace(" ", "")
    }
}

enum class Operator(
    private val symbol: Char,
    private val effect: Long.(Long) -> Long
) {
    ADDITION('+', Long::plus),
    MULTIPLICATION('*', Long::times);

    fun apply(one: Long, other: Long): Long = effect.invoke(one, other)

    companion object {
        fun parse(char: Char): Operator = entries.first { it.symbol == char }
    }
}
