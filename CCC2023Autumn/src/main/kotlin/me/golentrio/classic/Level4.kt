package me.golentrio.classic

import me.goldentrio.Contest
import me.goldentrio.source.standard.directory

fun main() = Contest({
    directory("CCC2023Autumn/classic/lvl4") {
        outExtension = "validator"
        expected("level4_example.in", "level4_example.validator")
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

        getRoute(size, p1, p2, map).forEach { (x, y) -> writeValue("$x,$y") }
        writeValue(lineBreak = true)
    }
}

private fun getRoute(size: Int, first: Pair<Int, Int>, second: Pair<Int, Int>, map: List<List<Type>>): List<Pair<Int, Int>> {
    val visited = mutableSetOf<Pair<Int, Int>>()
    val queue = mutableListOf<Pair<Pair<Int, Int>, List<Pair<Int, Int>>>>()

    queue.add(first to emptyList())

    while (queue.isNotEmpty()) {
        val (point, path) = queue.removeFirst()

        val newPath = path.toMutableList().also { it.add(point) }

        if (point == second) return newPath

        val (x, y) = point

        for (xMod in -1..1) {
            for (yMod in -1..1) {
                if (xMod == 0 && yMod == 0) continue

                val newX = x + xMod
                val newY = y + yMod

                if (newX !in 0..<size) continue
                if (newY !in 0..<size) continue

                val newPoint = newX to newY
                if (newPoint in visited) continue

                if (map[newY][newX] != Type.WATER) continue

                queue.add(newPoint to newPath)
                visited.add(newPoint)
            }
        }

//        if (x < size - 1 && x + 1 to y !in visited && map[y][x + 1] == Type.WATER) {
//            queue.add(x + 1 to y to newPath)
//            visited.add(x + 1 to y)
//        }
//
//        if (x > 0 && x - 1 to y !in visited && map[y][x - 1] == Type.WATER) {
//            queue.add(x - 1 to y to newPath)
//            visited.add(x - 1 to y)
//        }
//
//        if (y < size - 1 && x to y + 1 !in visited && map[y + 1][x] == Type.WATER) {
//            queue.add(x to y + 1 to newPath)
//            visited.add(x to y + 1)
//        }
//
//        if (y > 0 && x to y - 1 !in visited && map[y - 1][x] == Type.WATER) {
//            queue.add(x to y - 1 to newPath)
//            visited.add(x to y - 1)
//        }
    }

    throw IllegalStateException("No path found")
}