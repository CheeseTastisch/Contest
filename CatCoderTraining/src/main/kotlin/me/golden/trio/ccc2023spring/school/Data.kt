package me.golden.trio.ccc2023spring.school

enum class Cell(val char: Char) {

    EMPTY('O'),
    BARRIER('X'),
    WASP('W'),
    NON('-');

    override fun toString(): String = char.toString()

    companion object {

        fun fromChar(char: Char) = entries.first { it.char == char }

    }

}

enum class Direction(val xMod: Int, val yMod: Int) {

    UP_LEFT(-1, -1),
    UP_RIGHT(1, -1),
    RIGHT(2, 0),
    DOWN_RIGHT(1, 1),
    DOWN_LEFT(-1, 1),
    LEFT(-2, 0);

}

data class Nest(val cells: List<CellData>) {

    init {
        cells.forEach { it.nest = this }
    }

    fun getCell(x: Int, y: Int) = cells.firstOrNull { it.x == x && it.y == y }

    fun getWaspCell() = cells.firstOrNull { it.type == Cell.WASP }

    fun asString(): List<String> =
        cells.groupBy { it.y }
            .toSortedMap()
            .map { (_, cells) -> cells.sortedBy { it.x }
                .map { it.type }
                .joinToString("") }

    companion object {

        fun fromString(nest: List<String>): Nest {
            val cells = mutableListOf<CellData>()

            nest.forEachIndexed { y, line ->
                line.forEachIndexed { x, char ->
                    cells += CellData(x, y, Cell.fromChar(char))
                }
            }

            return Nest(cells)
        }

    }

}

data class CellData(var x: Int, var y: Int, var type: Cell) {

    lateinit var nest: Nest

    val coordinates
        get() = x to y

    fun getNeighbour(direction: Direction) = nest.getCell(x + direction.xMod, y + direction.yMod)

    fun getAllNeighbours() = Direction.entries.map { getNeighbour(it) }

    fun getNeighbours() = Direction.entries.mapNotNull { getNeighbour(it) }

}

