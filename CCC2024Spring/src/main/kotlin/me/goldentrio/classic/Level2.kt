package me.goldentrio.classic

import me.task.Task
import me.task.source.standard.directory
import me.task.source.standard.file
import me.task.util.tupple.MutablePair

fun main() = Task({
//    file("CCC2024Spring/classic/lvl2/level2_example.in") {
//        expected("CCC2024Spring/classic/lvl2/level2_example.out")
//    }
    directory("CCC2024Spring/classic/lvl2") {
        expected("level2_example.in", "level2_example.out")
    }
}) {
    readValues()
    while (hasNextLine()) {
        val directions = readValue(endOfLine = true)
            .map { Direction.fromIdentifier(it) }

        var minX = 0
        var minY = 0
        var maxX = 0
        var maxY = 0

        val position = MutablePair(0, 0)

        for (direction in directions) {
            when (direction) {
                Direction.NORTH -> {
                    position.second += 1
                    if (position.second > maxY) maxY = position.second
                }

                Direction.EAST -> {
                    position.first += 1
                    if (position.first > maxX) maxX = position.first
                }

                Direction.SOUTH -> {
                    position.second -= 1
                    if (position.second < minY) minY = position.second
                }

                Direction.WEST -> {
                    position.first -= 1
                    if (position.first < minX) minX = position.first
                }
            }
        }

        val width = maxX - minX + 1
        val height = maxY - minY + 1

        writeValue(width, height, lineBreak = true)
    }
}