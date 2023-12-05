package me.aoc.day2

enum class Result(val value: Int, val char: Char) {

    LOSE(0, 'X'),
    DRAW(3, 'Y'),
    WIN(6, 'Z');

    companion object {

        fun byChar(char: Char) = entries.first { char == it.char }

    }

}