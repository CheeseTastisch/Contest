package me.golden.trio.ccc2023spring.school

import me.task.Task
import me.task.source.standard.directory

fun main() = Task({
    directory("CatCoderTraining/ccc2023spring/school/level5/") {
        expected("level5_example.in", "level5_example.out")
    }

//    file("CatCoderTraining/ccc2023spring/school/level5/level5_example.in") {
//        expected("CatCoderTraining/ccc2023spring/school/level5/level5_example.out")
//    }
}) {
    readValues()
    readValues()

    var barriers = readInt(endOfLine = true)
    val currentNest = mutableListOf<String>()
    while (hasNextLine()) {
        if (readValue(peek = true).isEmpty()) {
            readValues()

            processNest(currentNest, barriers)
                .asString()
                .forEach { writeValue(it, lineBreak = true) }
            writeValue(lineBreak = true)

            currentNest.clear()
            barriers = readInt(endOfLine = true)
            continue
        }

        currentNest += readValue(endOfLine = true)
    }

    processNest(currentNest, barriers)
        .asString()
        .forEach { writeValue(it, lineBreak = true) }
}

private fun processNest(nestString: List<String>, amount: Int): Nest {
    val nest = Nest.fromString(nestString)

    val visited = mutableSetOf<Pair<Int, Int>>()
    val frequency = mutableMapOf<Pair<Int, Int>, Int>()

    depthFirstSearch(nest, nest.getWaspCell() ?: return nest, visited, frequency)

    frequency.entries
        .asSequence()
        .sortedByDescending { it.value }
        .map { it.key }
        .mapNotNull { (x, y) -> nest.getCell(x, y) }
        .take(amount)
        .forEach { it.type = Cell.BARRIER }

    return nest
}

private fun depthFirstSearch(
    nest: Nest,
    cell: CellData,
    visited: MutableSet<Pair<Int, Int>>,
    frequency: MutableMap<Pair<Int, Int>, Int>,
) {
    if (cell.coordinates in visited) return
    if (cell.type != Cell.EMPTY) return

    visited.add(cell.coordinates)
    frequency[cell.coordinates] = frequency.getOrDefault(cell.coordinates, 0) + 1

    val neighbors = cell.getNeighbours()
    for (neighbor in neighbors) {
        depthFirstSearch(nest, neighbor, visited, frequency)
    }
}