package me.golentrio.school

import java.util.UUID

data class Piece(
    var top: Type,
    var right: Type,
    var bottom: Type,
    var left: Type
) {

    fun rotate() = also {
        val temp = top
        top = left
        left = bottom
        bottom = right
        right = temp
    }

    fun equalsIgnoreOrientation(other: Piece): Boolean {
        if (this == other) return true
        if (this == other.rotate()) return true
        if (this == other.rotate()) return true
        if (this == other.rotate()) return true

        return false
    }

}


enum class Type(val type: Char) {

    KNOB('K') {
        override fun invert() = HOLE
    },
    HOLE('H') {
        override fun invert() = KNOB
    },
    EDGE('E') {
        override fun invert() = EDGE
    };

    abstract fun invert(): Type

    override fun toString(): String {
        return type.toString()
    }

    companion object {

        fun byType(type: Char) = entries.first { it.type == type }

    }

}

fun <T> List<T>.groupByCompare(compare: (T, T) -> Boolean): Map<T, List<T>> {
    val groups = mutableMapOf<T, MutableList<T>>()

    for (item in this) {
        var found = false

        for (group in groups) {
            if (compare(item, group.key)) {
                group.value.add(item)
                found = true
                break
            }
        }

        if (!found) {
            groups[item] = mutableListOf(item)
        }
    }

    return groups
}