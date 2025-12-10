package day10

class Machines(
    private val machines: List<Machine>
) {
    fun fewestTotalPresses(): Int = machines.sumOf { it.fewestTotalPresses() }

    companion object {
        fun parse(lines: List<String>): Machines = Machines(lines.map { Machine.parse(it) })
    }
}

class Machine(
    private val lights: Lights,
    private val buttons: List<Button>
) {
    fun fewestTotalPresses(): Int = generateSequence(
        listOf(LightsConfiguration(lights.off(), listOf())),
        ::solve
    )
        .last()
        .maxOf { it.presses }


    private fun solve(previousConfigurations: List<LightsConfiguration>): List<LightsConfiguration>? {
        val level = previousConfigurations.maxOf { it.presses }
        val candidates = previousConfigurations
            //  Filter: Dead-end previous solutions
            .filter { it.presses == level }
        val solution = candidates.find { it.lights.identical(lights) }
        if (solution != null) return null
        val nextConfigurations = buttons.flatMap { button ->
            candidates.map { configuration ->
                LightsConfiguration(
                    configuration.lights.press(button),
                    configuration.buttonsPressed + button
                )
            }
        }
        //  Filter: Solutions that lead to previously occurring light-state
        val nextUniqueConfigurations = nextConfigurations.filter { nextConfiguration ->
            previousConfigurations.none { previousConfiguration ->
                previousConfiguration.lights.identical(nextConfiguration.lights)
            }
        }
        return previousConfigurations + nextUniqueConfigurations
    }

    companion object {
        fun parse(line: String): Machine = line.split(' ').let { allTokens ->
            Machine(
                Lights.parse(allTokens.first()),
                allTokens.drop(1).dropLast(1).map { buttonToken -> Button.parse(buttonToken) }
            )
        }
    }
}

class Lights(
    val indicators: List<Boolean>
) {
    fun off(): Lights = Lights(indicators.map { false })

    fun press(button: Button) = indicators
        .mapIndexed { index, indicator -> if (button.toggles.contains(index)) !indicator else indicator }
        .let { Lights(it) }

    fun identical(other: Lights): Boolean = indicators == other.indicators

    companion object {
        fun parse(line: String): Lights = Lights(line.drop(1).dropLast(1).map { it == '#' })
    }
}

class Button(
    val toggles: List<Int>
) {
    companion object {
        fun parse(line: String): Button = Button(line.drop(1).dropLast(1).split(',').map { it.toInt() })
    }
}

data class LightsConfiguration(
    val lights: Lights,
    val buttonsPressed: List<Button>
) {
    val presses get(): Int = buttonsPressed.size
}