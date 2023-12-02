package me.golentrio.school

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

        override fun fits(other: Type) = other == HOLE
    },
    HOLE('H') {
        override fun invert() = KNOB

        override fun fits(other: Type) = other == KNOB
    },
    EDGE('E') {
        override fun invert() = EDGE

        override fun fits(other: Type) = other == EDGE
    };

    abstract fun invert(): Type

    abstract fun fits(other: Type): Boolean

    override fun toString(): String {
        return type.toString()
    }

    companion object {

        fun byType(type: Char) = entries.first { it.type == type }

    }

}