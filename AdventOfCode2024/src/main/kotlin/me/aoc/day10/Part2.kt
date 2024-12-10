package me.aoc.day10

import me.task.Task
import me.task.source.standard.inferFile
import me.task.source.standard.string

fun main() = Task({
    string(
        """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        10456732
    """.trimIndent()
    ) {
        expected = "81"
    }

    inferFile("day10.in") {
        outExtension = "part2"
    }
}) {
    val map = readAllValues().map { it.map { char -> char.toString().toInt() } }

    writeValue(
        map.flatMapIndexed { y, row ->
            row.mapIndexedNotNull { x, value ->
                if (value == 0) x to y
                else null
            }
        }.sumOf { getTops(it, map).size })
}

private fun getTops(start: Pair<Int, Int>, map: List<List<Int>>): List<Pair<Int, Int>> {
    val current = map[start.second][start.first]
    return Direction.entries.flatMap {
        val x = start.first + it.delta.first
        val y = start.second + it.delta.second
        val next = map.getOrNull(y)?.getOrNull(x)

        if (current + 1 != next) return@flatMap emptySet()
        if (next == 9) return@flatMap listOf(x to y)

        getTops(x to y, map)
    }
}