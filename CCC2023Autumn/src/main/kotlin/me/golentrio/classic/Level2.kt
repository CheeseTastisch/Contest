package me.golentrio.classic

import me.goldentrio.Contest
import me.goldentrio.source.standard.directory

fun main() = Contest({
    directory("CCC2023Autumn/classic/lvl2") {
        outExtension = "validator"
        expected("level2_example.in", "level2_example.validator")
    }
}) {
    val size = readInt(endOfLine = true)

    val map = mutableListOf<MutableList<Type>>()
    for (i in 0..<size) {
        map.add(readValue(endOfLine = true).map { Type.byChar(it) }.toMutableList())
    }

    readValue(endOfLine = true)

    while (hasNextLine()) {
        val (p1, p2) = readValues()
            .map {
                it.split(",")
                    .map { point -> point.toInt() }
                    .let { (x, y) -> x to y }
            }

        writeValue(
            if (isOnSameIsland(p1, p2, map)) "SAME"
            else "DIFFERENT",
            lineBreak = true
        )
    }
}

private fun isOnSameIsland(first: Pair<Int, Int>, second: Pair<Int, Int>, map: List<List<Type>>): Boolean {
    val visited = mutableSetOf<Pair<Int, Int>>()
    val queue = mutableListOf<Pair<Int, Int>>()

    queue.add(first)

    while (queue.isNotEmpty()) {
        val (x, y) = queue.removeFirst()

        if (x to y == second) return true

        if (x < map[y].size && x + 1 to y !in visited && map[y][x + 1] != Type.WATER) {
            queue.add(x + 1 to y)
            visited.add(x + 1 to y)
        }

        if (x > 0 && x - 1 to y !in visited && map[y][x - 1] != Type.WATER) {
            queue.add(x - 1 to y)
            visited.add(x - 1 to y)
        }

        if (y < map.size && x to y + 1 !in visited && map[y + 1][x] != Type.WATER) {
            queue.add(x to y + 1)
            visited.add(x to y + 1)
        }

        if (y > 0 && x to y - 1 !in visited && map[y - 1][x] != Type.WATER) {
            queue.add(x to y - 1)
            visited.add(x to y - 1)
        }
    }

    return false
}