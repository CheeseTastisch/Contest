package me.aoc._2

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string(
        """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    """.trimIndent()
    ) {
        expected = "8"
    }
    file("AdventOfCode2023/in/_2/part1.in")
}) {
    var value = 0
    while (hasNextLine()) {
        readValue()
        val id = readValue().replace(":", "").toInt()
        var possibile = true

        while (hasNextValue()) {
            val amount = readInt()
            val color = Color.fromIdentifier(
                readValue()
                    .replace(",", "")
                    .replace(";", "")
            )

            when(color) {
                Color.RED -> if (amount > 12) possibile = false
                Color.GREEN -> if (amount > 13) possibile = false
                Color.BLUE -> if (amount > 14) possibile = false
            }

            if (!possibile) break
        }

        if (possibile) value += id
        readValues()
    }
    writeValue(value)
}