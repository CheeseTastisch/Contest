package me.golden.trio.ccc2023spring.school

import me.goldentrio.Contest
import me.goldentrio.source.standard.directory

fun main() = Contest({
    directory("CatCoderTraining/ccc2023spring/school/level4/") {
        expected("level4_example.in", "level4_example.out")
    }
}) {
    readValues()
    readValues()

    val currentNest = mutableListOf<String>()
    while (hasNextLine()) {
        if (readValue(peek = true).isEmpty()) {
            readValues()

            writeValue(if (processNest(currentNest)) "FREE" else "TRAPPED", lineBreak = true)

            currentNest.clear()
            continue
        }

        currentNest += readValue(endOfLine = true)
    }

    writeValue(if (processNest(currentNest)) "FREE" else "TRAPPED", lineBreak = true)
}

private fun processNest(nest: List<String>): Boolean {
    val queue = mutableListOf(Nest.fromString(nest).getWaspCell() ?: return false)
    val visited = mutableSetOf<Pair<Int, Int>>()

    while (queue.isNotEmpty()) {
        for (neighbour in queue.removeFirst().getAllNeighbours()) {
            if (neighbour == null) return true
            if (visited.contains(neighbour.x to neighbour.y)) continue

            visited += neighbour.x to neighbour.y
            if (neighbour.type == Cell.EMPTY) queue += neighbour
        }
    }

    return false
}