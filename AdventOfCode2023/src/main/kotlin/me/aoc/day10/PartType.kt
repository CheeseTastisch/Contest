package me.aoc.day10

enum class PartType(
    val identifier: Char,
    val first: Direction?,
    val second: Direction?,
) {

    VERTICAL('|', Direction.NORTH, Direction.SOUTH),
    HORIZONTAL('-', Direction.WEST, Direction.EAST),
    NORTH_EAST('L', Direction.NORTH, Direction.EAST),
    NORTH_WEST('J', Direction.NORTH, Direction.WEST),
    SOUTH_WEST('7', Direction.SOUTH, Direction.WEST),
    SOUTH_EAST('F', Direction.SOUTH, Direction.EAST),
    GROUND('.', null, null),
    START('S', null, Direction.EAST);

    override fun toString() = when (this) {
        VERTICAL -> "│"
        HORIZONTAL -> "─"
        NORTH_EAST -> "└"
        NORTH_WEST -> "┘"
        SOUTH_WEST -> "┐"
        SOUTH_EAST -> "┌"
        GROUND -> " "
        START -> "S"
    }

    companion object {
        fun fromIdentifier(identifier: Char) = entries.first { it.identifier == identifier }
    }

}