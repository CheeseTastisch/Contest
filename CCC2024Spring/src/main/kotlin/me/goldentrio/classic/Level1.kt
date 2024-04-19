package me.goldentrio.classic

import me.task.Task
import me.task.source.standard.directory
import me.task.source.standard.file

fun main() = Task({
//    file("CCC2024Spring/classic/lvl1/level1_example.in") {
//        expected("CCC2024Spring/classic/lvl1/level1_example.out")
//    }
    directory("CCC2024Spring/classic/lvl1") {
        expected("level1_example.in", "level1_example.out")
    }
}) {
    readValues()
    while (hasNextLine()) {
        val directions = readValue(endOfLine = true)
            .map { Direction.fromIdentifier(it) }
            .groupBy { it }
            .map { it.key to it.value.size }
            .toMap()

        writeValue(
            directions[Direction.NORTH] ?: 0,
            directions[Direction.EAST] ?: 0,
            directions[Direction.SOUTH] ?: 0,
            directions[Direction.WEST] ?: 0,
            lineBreak = true,
        )
    }
}