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
        expected = "9"
    }

    inferFile("day4.in") {
        outExtension = "part2"
    }
}) {
    val map = readAllValues().map { it.map { char -> Letter.fromCode(char) } }
    writeValue(
        map.indices
            .flatMap { x -> map[x].indices.map { y -> x to y } }
            .count { map.isMasX(it) }
    )
}

val adjacentDelta = listOf(
    (-1 to -1) to (1 to 1),
    (-1 to 1) to (1 to -1),
)

fun List<List<Letter>>.isMasX(start: Pair<Int, Int>): Boolean =
    this[start.first][start.second] == Letter.A && adjacentDelta.all { (previous, next) ->
        val previousLetter = this.getOrNull(start.first + previous.first)?.getOrNull(start.second + previous.second)
        val nextLetter = this.getOrNull(start.first + next.first)?.getOrNull(start.second + next.second)

        if (previousLetter == null || nextLetter == null) return@all false

        (previousLetter == Letter.A.previous && nextLetter == Letter.A.next) ||
                (previousLetter == Letter.A.next && nextLetter == Letter.A.previous)
    }