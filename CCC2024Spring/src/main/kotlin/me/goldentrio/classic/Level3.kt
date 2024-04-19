package me.goldentrio.classic

import me.task.Task
import me.task.source.standard.directory
import me.task.util.tupple.MutablePair

fun main() = Task({
//    file("CCC2024Spring/classic/lvl3/level3_example.in") {
//        expected("CCC2024Spring/classic/lvl3/level3_example.out")
//    }
    directory("CCC2024Spring/classic/lvl3") {
        expected("level3_example.in", "level3_example.out")
    }
}) {
    readValues()
    lawn@ while (hasNextLine()) {
        val (inWidth, inHeight) = readInts()

        val grid = mutableListOf<List<CellType>>()
        for (i in 0..<inHeight) {
            grid.add(readValue(endOfLine = true).map { CellType.fromIdentifier(it) })
        }

        val treeCell = grid
            .flatMapIndexed { y, row -> row.mapIndexed { x, cell -> Triple(x, y, cell) } }
            .first { it.third == CellType.TREE }
            .let { it.first to it.second}

        val directions = readValue(endOfLine = true).map { Direction.fromIdentifier(it) }

        val path = mutableListOf<Pair<Int, Int>>()

        val position = MutablePair(0, 0)
        path.add(position.first to position.second)

        for (direction in directions) {
            when (direction) {
                Direction.NORTH -> position.second -= 1
                Direction.EAST -> position.first += 1
                Direction.SOUTH -> position.second += 1
                Direction.WEST -> position.first -= 1
            }

            path.add(position.first to position.second)
        }

        val minX = path.minOf { it.first }
        val maxX = path.maxOf { it.first }
        val minY = path.minOf { it.second }
        val maxY = path.maxOf { it.second }

        val width = maxX - minX + 1
        val height = maxY - minY + 1

        if (width != inWidth || height != inHeight) {
            writeValue("INVALID", lineBreak = true)
            continue@lawn
        }

        if (path.groupBy { it }.any { it.value.size > 1 }) {
            writeValue("INVALID", lineBreak = true)
            continue@lawn
        }

        val emptyCells = (minX..maxX)
            .flatMap { x -> (minY..maxY).map { y -> x to y } }
            .filter { it !in path }

        if (emptyCells.size != 1) {
            writeValue("INVALID", lineBreak = true)
            continue@lawn
        }

        val emptyCell = emptyCells.first()

        var currentX = treeCell.first - emptyCell.first
        var currentY = treeCell.second - emptyCell.second

        val validWidth = 0..<width
        val validHeight = 0..<height

        for (direction in directions) {
            when (direction) {
                Direction.NORTH -> currentY -= 1
                Direction.EAST -> currentX += 1
                Direction.SOUTH -> currentY += 1
                Direction.WEST -> currentX -= 1
            }

            if (currentX !in validWidth || currentY !in validHeight) {
                writeValue("INVALID", lineBreak = true)
                continue@lawn
            }
        }

        writeValue("VALID", lineBreak = true)
    }
}