package me.golden.trio.ccc2023spring.school

import me.task.Task
import me.task.source.standard.directory

fun main() = Task({
    directory("CatCoderTraining/ccc2023spring/school/level3/") {
        expected("level3_example.in", "level3_example.out")
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
    val wasp = Nest.fromString(nest).getWaspCell() ?: return false

    for (direction in Direction.entries) {
        var currentCell = wasp
        while (true) {
            currentCell = currentCell.getNeighbour(direction) ?: return true
            if (currentCell.type == Cell.BARRIER) break
        }
    }

    return false
}