package no.breaks.mastermind

enum class Color(val shortName: Char, val longName: String) {

    RED('R', "Red"),
    GREEN('G', "Green"),
    BLUE('B', "Blue"),
    YELLOW('Y', "Yellow"),
    ORANGE('O', "Orange"),
    PURPLE('P', "Purple");

    companion object {
        val shortNames = values().map { it.shortName }.toSet()

        fun fromShortName(shortName: Char): Color {
            return values().first { it.shortName == shortName }
        }
    }
}