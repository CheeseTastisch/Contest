package me.goldentrio.classic

enum class Direction(val identifier: Char, val deltaX: Int, val deltaY: Int) {

    NORTH('W', 0, -1),
    EAST('D', 1, 0),
    SOUTH('S', 0, 1),
    WEST('A', -1, 0);

    companion object {

        fun fromIdentifier(identifier: Char): Direction {
            return entries.first { it.identifier == identifier }
        }

        fun fromDelta(deltaX: Int, deltaY: Int): Direction {
            return entries.first { it.deltaX == deltaX && it.deltaY == deltaY }
        }

    }

}

enum class CellType(val identifier: Char) {

    EMPTY('.'),
    TREE('X');

    companion object {

        fun fromIdentifier(identifier: Char): CellType {
            return entries.first { it.identifier == identifier }
        }

    }

}