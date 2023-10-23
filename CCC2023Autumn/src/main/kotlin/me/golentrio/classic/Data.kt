package me.golentrio.classic

enum class Type(val char: Char) {

    LAND('L'),
    WATER('W');

    companion object {

        fun byChar(char: Char) = entries.first { it.char == char }

    }

}