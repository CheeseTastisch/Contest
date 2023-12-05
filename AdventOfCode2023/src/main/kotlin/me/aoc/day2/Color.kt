package me.aoc.day2

enum class Color(val identifier: String) {

    BLUE("blue"),
    RED("red"),
    GREEN("green");

    companion object {

        fun fromIdentifier(identifier: String) = entries.first { it.identifier == identifier }

    }

}