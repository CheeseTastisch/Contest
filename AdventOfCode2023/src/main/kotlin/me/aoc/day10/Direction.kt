package me.aoc.day10

enum class Direction(
    val delta: Pair<Int, Int>,
) {

    NORTH(0 to -1),
    EAST(1 to 0),
    SOUTH(0 to 1),
    WEST(-1 to 0);

    val inverse by lazy {
        when (this) {
            NORTH -> SOUTH
            EAST -> WEST
            SOUTH -> NORTH
            WEST -> EAST
        }
    }

}