package me.aoc.day9

enum class Direction(val identifier: Char, val motion: Pair<Int, Int>) {

    UP('U', 0 to -1),
    DOWN('D', 0 to 1),
    LEFT('L', -1 to 0),
    RIGHT('R', 1 to 0);

    companion object {

        fun byIdentifier(identifier: Char): Direction {
            return entries.first { it.identifier == identifier }
        }

    }

}