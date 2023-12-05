package me.aoc.day2

enum class Option(val value: Int, vararg val chars: Char) {

    ROCK(1, 'A', 'X'),
    PAPER(2, 'B', 'Y'),
    SCISSORS(3, 'C', 'Z');

    fun getScore(other: Option): Int {
        return when (this) {
            ROCK -> when (other) {
                ROCK -> 3
                PAPER -> 0
                SCISSORS -> 6
            }

            PAPER -> when (other) {
                ROCK -> 6
                PAPER -> 3
                SCISSORS -> 0
            }

            SCISSORS -> when (other) {
                ROCK -> 0
                PAPER -> 6
                SCISSORS -> 3
            }
        }
    }

    fun getMe(result: Result) = when (this) {
        ROCK -> when (result) {
            Result.WIN -> PAPER
            Result.DRAW -> ROCK
            Result.LOSE -> SCISSORS
        }

        PAPER -> when (result) {
            Result.WIN -> SCISSORS
            Result.DRAW -> PAPER
            Result.LOSE -> ROCK
        }

        SCISSORS -> when (result) {
            Result.WIN -> ROCK
            Result.DRAW -> SCISSORS
            Result.LOSE -> PAPER
        }
    }

    companion object {

        fun byChar(char: Char) = entries.first { char in it.chars }

    }

}