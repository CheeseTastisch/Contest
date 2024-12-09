package me.aoc.util

class ChangingValue<T>(var value: T) {

    fun modify(modifier: (T) -> T) {
        value = modifier(value)
    }

    override fun toString() = "ChangingValue(value=$value)"

}

fun <T> List<ChangingValue<T>>.modify(modifier: (T) -> T) {
    forEach { it.modify(modifier) }
}