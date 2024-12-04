package me.aoc.day4

enum class Letter(val code: Char, val done: Boolean = false) {
    X('X'),
    M('M'),
    A('A'),
    S('S', true);

    val next: Letter?
        get() = when (this) {
            X -> M
            M -> A
            A -> S
            S -> null
        }

    val previous: Letter?
        get() = when (this) {
            X -> null
            M -> X
            A -> M
            S -> A
        }

    companion object {
        fun fromCode(code: Char): Letter = entries.first { it.code == code }
    }

}