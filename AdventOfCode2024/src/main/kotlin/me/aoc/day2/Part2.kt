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
        expected = "4"
    }

    inferFile("day2.in") {
        outExtension = "part2"
    }
}) {
    var save = 0

    while (hasNextLine()) {
        val values = readInts()

        val grouped = values.grouped()
        val mostCommonSign = grouped
            .groupBy { (it.first - it.second).unitary() }
            .maxBy { it.value.size }
            .key

        if (grouped.isValid(mostCommonSign)) save++
        else {
            for (i in values.indices) {
                val newList = values.toMutableList()
                newList.removeAt(i)

                val newGrouped = newList.grouped()
                if (newGrouped.isValid(mostCommonSign)) {
                    save++
                    break
                }
            }
        }
    }

    writeValue(save)
}