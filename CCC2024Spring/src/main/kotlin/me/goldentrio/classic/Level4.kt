package me.goldentrio.classic

import me.task.Task
import me.task.source.standard.directory
import me.task.source.standard.file

fun main() = Task({
    file("CCC2024Spring/classic/lvl4/level4_example.in") {
        expected("CCC2024Spring/classic/lvl4/level4_example.out")
    }
//    directory("CCC2024Spring/classic/lvl4") {
//        expected("level4_example.in", "level4_example.out")
//    }
}) {
    readValues()
    while (hasNextLine()) {
        val (width, height) = readInts()

        val grid = mutableListOf<List<CellType>>()
        for (i in 0..<height) {
            grid.add(readValue(endOfLine = true).map { CellType.fromIdentifier(it) })
        }

        writeValue(
            solve(
                0,
                0,
                Array(height) { y -> Array(width) { x -> grid[y][x] == CellType.TREE } },
                Direction.EAST
            )?.joinToString("") { it.identifier.toString() } ?: ":/",
            lineBreak = true
        )
//        writeValue(LawnMower(width, height, tree).solve().joinToString("") { it.identifier.toString() }, lineBreak = true)
//        println("one done")
    }
}

private fun solve(x: Int, y: Int, grid: Array<Array<Boolean>>, previousDirection: Direction): List<Direction>? {
    if (grid.all { row -> row.all { it } }) return emptyList()

    var newX = x + previousDirection.deltaX
    var newY = y + previousDirection.deltaY

    if (isValid(newX, newY, grid)) {
        grid[newY][newX] = true
        val result = solve(newX, newY, grid, previousDirection)
        if (result != null) return result + previousDirection

        grid[newY][newX] = false
    }

    for (direction in Direction.values()) {
        if (direction == previousDirection) continue

        newX = x + direction.deltaX
        newY = y + direction.deltaY

        if (isValid(newX, newY, grid)) {
            grid[newY][newX] = true
            val result = solve(newX, newY, grid, direction)
            if (result != null) return result + direction

            grid[newY][newX] = false
        }
    }

    return null
}

private fun isValid(x: Int, y: Int, grid: Array<Array<Boolean>>): Boolean {
    return x in 0..<grid[0].size && y in 0..<grid.size && !grid[y][x]
}

//class LawnMower(val width: Int, val height: Int, val treePosition: Pair<Int, Int>) {
//    private val lawn = Array(height) { IntArray(width) { 0 } }
////    private val directions = arrayOf(Pair(1, 0), Pair(0, 1), Pair(-1, 0), Pair(0, -1))
//
//    init {
//        // Baum platzieren (wird als -1 markiert)
//        lawn[treePosition.second][treePosition.first] = -1
//    }
//
//    fun solve(): List<Direction> {
//        // Anfangsposition wählen, kann optimiert werden
//        return mow(0, 0) ?: throw IllegalStateException("No solution found")
//    }
//
//    private fun mow(x: Int, y: Int): List<Direction>? {
//        if (!isValid(x, y)) return null
//
//        lawn[y][x] = 1 // Aktuelle Position als besucht markieren
//
//        if (isComplete()) return emptyList()
//
//        val nextMoves = Direction.entries
//            .map { Pair(x + it.deltaX, y + it.deltaY) }
//            .filter { isValid(it.first, it.second) }
//            .sortedBy { countNextValidMoves(it.first, it.second) }
//
//        for (move in nextMoves) {
//            val moves = mow(move.first, move.second)
//            if (moves != null) {
//                return listOf(Direction.fromDelta(move.first - x, move.second - y)) + moves
//            }
//        }
//
//        lawn[y][x] = 0 // Zurücksetzen (Backtracking)
//        return null
//    }
//
//    private fun isValid(x: Int, y: Int): Boolean {
//        return x in 0..<width && y >= 0 && y < height && lawn[y][x] == 0
//    }
//
//    private fun isComplete(): Boolean {
//        return lawn.all { row -> row.all { cell -> cell != 0 } }
//    }
//
//    private fun countNextValidMoves(x: Int, y: Int): Int {
//        return Direction.entries.count { isValid(x + it.deltaX, y + it.deltaY) }
//    }
//}
