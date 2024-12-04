package me.aoc.day4

import me.task.Task
import me.task.source.standard.inferFile
import me.task.source.standard.string

fun main() = Task({
    string(
        """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX
    """.trimIndent()
    ) {
        expected = "18"
    }

    inferFile("day4.in") {
        outExtension = "part1"
    }
}) {
    val map = readAllValues().map { it.map { char -> Letter.fromCode(char) } }
    writeValue(
        map.indices
            .flatMap { x -> map[x].indices.map { y -> x to y } }
            .sumOf { map.countXMas(it) }
    )
}

val delta = (-1..1)
    .flatMap { x -> (-1..1).map { y -> x to y } }
    .filter { it != 0 to 0 }

fun List<List<Letter>>.countXMas(start: Pair<Int, Int>): Int =
    if (this[start.first][start.second] != Letter.X) 0
    else delta.count { (dx, dy) ->
        var currentLetter = Letter.X
        var (x, y) = start

        while (true) {
            x += dx
            y += dy

            val expectedNext = currentLetter.next
            val actualNext = this.getOrNull(x)?.getOrNull(y) ?: break // Out of bounds

            if (expectedNext != actualNext) break // Wrong letter
            if (actualNext.done) return@count true // Done

            currentLetter = actualNext
        }

        false

    }