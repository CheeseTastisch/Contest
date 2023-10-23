package me.golentrio.classic

import me.goldentrio.Contest
import me.goldentrio.source.standard.directory
import me.goldentrio.source.standard.file
import kotlin.math.abs

fun main() = Contest({
//    directory("CCC2023Autumn/classic/lvl5") {
//        outExtension = "validator"
//        expected("level5_example.in", "level5_example.validator")
//    }
    file("CCC2023Autumn/classic/lvl5/level5_example.in") {
        outExtension = "validator"
        expected("CCC2023Autumn/classic/lvl5/level5_example.validator")
    }
}) {
    val size = readInt(endOfLine = true)

    val map = mutableListOf<List<Type>>()
    for (i in 0..<size) {
        map.add(listOf(*readWholeLine().map { Type.byChar(it) }.toTypedArray()))
    }

    readLine()

    while (hasNextLine()) {
        val (x, y) = readValue(endOfLine = true)
            .split(",")
            .map { it.toInt() }

        getRoute(x, y, map).forEach { (x, y) -> writeValue("$x,$y") }
        writeBreak()
    }
}

private const val MIN_PATH_LENGTH = 3

private fun getNeighbours(x: Int, y: Int, map: List<List<Type>>): List<Pair<Int, Int>> {
    val neighbours = mutableListOf<Pair<Int, Int>>()

    val visited = Array(map.size) { BooleanArray(map[0].size) }
    val stack = mutableListOf<Pair<Int, Int>>()

    val directions = listOf(
        0 to -1, // Up
        0 to 1,  // Down
        -1 to 0, // Left
        1 to 0   // Right
    )

    stack.add(x to y)
    while (stack.isNotEmpty()) {
        val (currentX, currentY) = stack.removeAt(stack.size - 1)

        // Check if out of bounds or already visited
        if (currentY < 0 || currentY >= map.size || currentX < 0 || currentX >= map[currentY].size || visited[currentY][currentX]) {
            continue
        }

        visited[currentY][currentX] = true

        if (map[currentY][currentX] == Type.WATER) {
            neighbours.add(currentX to currentY)
        }

        for ((dx, dy) in directions) {
            val nextX = currentX + dx
            val nextY = currentY + dy

            if (nextY in map.indices && nextX in map[0].indices && !visited[nextY][nextX] && map[nextY][nextX] == Type.WATER) {
                stack.add(nextX to nextY)
            }
        }
    }

    return neighbours
}

private fun getRoute(x: Int, y: Int, map: List<List<Type>>): List<Pair<Int, Int>> {
    val path = mutableListOf<Pair<Int, Int>>()
    val neighbours = getNeighbours(x, y, map)

    if (neighbours.isEmpty()) return emptyList()

    val visited = Array(map.size) { BooleanArray(map[0].size) }
    var currentTile = neighbours[0]

    val allDirections = listOf(
        -1 to -1, // Top left
        0 to -1,  // Up
        1 to -1,  // Top right
        -1 to 0,  // Left
        1 to 0,   // Right
        -1 to 1,  // Bottom left
        0 to 1,   // Down
        1 to 1    // Bottom right
    )

    do {
        path.add(currentTile)
        visited[currentTile.second][currentTile.first] = true

        var foundNextTile = false

        for ((dx, dy) in allDirections) {
            val nextX = currentTile.first + dx
            val nextY = currentTile.second + dy

            if (nextY in map.indices && nextX in map[nextY].indices && map[nextY][nextX] == Type.WATER && !visited[nextY][nextX]) {
                currentTile = nextX to nextY
                foundNextTile = true
                break
            }
        }

        // If no next tile was found, break
        if (!foundNextTile) break

    } while (!isAdjacentToStart(path[0], currentTile) || path.size < MIN_PATH_LENGTH)

    return if (path.size >= MIN_PATH_LENGTH && isAdjacentToStart(path[0], currentTile)) path else emptyList()
}

private fun isAdjacentToStart(start: Pair<Int, Int>, current: Pair<Int, Int>): Boolean {
    return abs(start.first - current.first) <= 1 && abs(start.second - current.second) <= 1
}
