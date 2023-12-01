package me.golden.trio.ccc2023spring.school

import me.task.Task
import me.task.source.standard.directory

fun main() = Task({
    directory("CatCoderTraining/ccc2023spring/school/level2/") {
        expected("level2_example.in", "level2_example.out")
    }
}) {
    readValues()
    readValues()

    val currentNest = mutableListOf<String>()
    while (hasNextLine()) {
        if (readValue(peek = true).isEmpty()) {
            readValues()

            writeValue(processNest(currentNest), lineBreak = true)

            currentNest.clear()
            continue
        }

        currentNest += readValue(endOfLine = true)
    }

    writeValue(processNest(currentNest), lineBreak = true)
}

private fun processNest(nest: List<String>) = Nest.fromString(nest)
    .getWaspCell()
    ?.getNeighbours()
    ?.filter { it.type == Cell.EMPTY }
    ?.size
    ?: 0