package me.aoc.day3

enum class CellType {

    NUMBER,
    PART,
    EMPTY

}

data class Number(val xStart: Int, val xEnd: Int, val y: Int, val value: Int) {

    fun hasAdjacentToPart(grid: Grid): Boolean {
        val adjacentCells = mutableSetOf(
            Pair(xStart - 1, y - 1),
            Pair(xStart - 1, y),
            Pair(xStart - 1, y + 1),
            Pair(xEnd + 1, y - 1),
            Pair(xEnd + 1, y),
            Pair(xEnd + 1, y + 1),
        )

        (-1..1)
            .map { it to xStart..xEnd }
            .flatMap { (deltaY, x) -> x.map { it to y + deltaY } }
            .forEach { (x, y) -> adjacentCells.add(Pair(x, y)) }

        return adjacentCells.any { (x, y) -> grid.cells.getOrNull(y)?.getOrNull(x) == CellType.PART }
    }

}

data class PossibleGears(val x: Int, val y: Int) {

    fun getAdjacentNumbers(grid: Grid) = (-1..1)
        .flatMap { deltaX -> (-1..1).map { deltaY -> deltaX to deltaY } }
        .filter { (deltaX, deltaY) -> deltaX != 0 || deltaY != 0 }
        .map { (deltaX, deltaY) -> x + deltaX to y + deltaY }
        .mapNotNull { (x, y) -> grid.getNumberInCell(x, y) }
        .distinct()

    fun getRatio(grid: Grid) = getAdjacentNumbers(grid)
        .let { numbers ->
            if (numbers.size != 2) return@let 0
            else numbers.map { it.value }.reduce { a, b -> a * b }
        }

}

data class Grid(
    val rows: Int,
    val columns: Int,
    val cells: MutableList<List<CellType>> = mutableListOf(),
    val numbers: MutableList<Number> = mutableListOf(),
    val possibleGears: MutableList<PossibleGears> = mutableListOf(),
) {

    fun getNumberInCell(x: Int, y: Int) = numbers.firstOrNull { it.xStart <= x && it.xEnd >= x && it.y == y }

}