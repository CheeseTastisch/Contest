package me.aoc.day2

import me.task.Task
import me.task.source.standard.inferFile
import me.task.source.standard.string
import kotlin.math.absoluteValue

fun main() = Task({
    string(
        """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent()
    ) {
        expected = "2"
    }

    inferFile("day2.in") {
        outExtension = "part1"
    }
}) {
    var save = 0

    while (hasNextLine()) {
        val values = readInts().grouped()
        val mostCommonSign = values
            .groupBy { (it.first - it.second).unitary() }
            .maxBy { it.value.size }
            .key

        if (values.isValid(mostCommonSign)) save++
    }

    writeValue(save)
}

fun List<Int>.grouped(): List<Pair<Int, Int>> {
    return (0..<size - 1).map { get(it) to get(it + 1) }
}

fun Int.unitary(): Int {
    return if (this > 0) 1 else -1
}

fun List<Pair<Int, Int>>.isValid(mostCommonSign: Int): Boolean {
    return this
        .map { it.first - it.second }
        .map { it.unitary() to it.absoluteValue }
        .all { (unitary, distance) -> unitary == mostCommonSign && distance in 1..3 }
}