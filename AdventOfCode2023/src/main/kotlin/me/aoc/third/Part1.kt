package me.aoc.third

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string
import java.lang.StringBuilder

fun main() = Task({
    string(
        """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...${'$'}.*....
        .664.598..
    """.trimIndent()
    ) {
        expected = "4361"
    }
    file("AdventOfCode2023/in/third/part1.in")
}) {
    val grid = Grid(rows = inputQueue.size, columns = readValue(true).length)

    while (hasNextLine()) {
        val line = readValue(endOfLine = true)

        val row = mutableListOf<CellType>()

        var numberStart = -1
        val number = StringBuilder()

        line.forEachIndexed { index, char ->
            if (char.isDigit()) {
                if (numberStart == -1) numberStart = index
                number.append(char)

                row.add(CellType.NUMBER)
            } else {
                if (numberStart != -1) {
                    val numberEnd = index - 1
                    val numberValue = number.toString().toInt()

                    grid.numbers.add(Number(numberStart, numberEnd, grid.cells.size, numberValue))

                    numberStart = -1
                    number.clear()
                }

                when (char) {
                    '.' -> row.add(CellType.EMPTY)
                    else -> row.add(CellType.PART)
                }
            }
        }

        if (numberStart != -1) {
            val numberEnd = line.length - 1
            val numberValue = number.toString().toInt()

            grid.numbers.add(Number(numberStart, numberEnd, grid.cells.size, numberValue))
        }

        grid.cells.add(row)
    }

    writeValue(
        grid.numbers
            .filter { it.hasAdjacentToPart(grid) }
            .sumOf { it.value }
    )
}