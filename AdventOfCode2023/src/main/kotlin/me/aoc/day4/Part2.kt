package me.aoc.day4

import me.aoc.util.ChangingValue
import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string(
        """
        Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
        Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
        Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
        Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
        Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
    """.trimIndent()
    ) {
        expected = "30"
    }
    file("AdventOfCode2023/in/day4/part2.in")
}) {
    val cards = mutableMapOf<Int, ChangingValue<Int>>()
    for (i in 1..inputQueue.size) cards[i] = ChangingValue(1)

    while (hasNextLine()) {
        val inputRow = readValues().joinToString(" ").split(":")
        val cardNumber = inputRow[0].split(" ").last().toInt()

        val (winningNumbers, ownNumbers) = inputRow[1].split("|")
            .map { numbers -> numbers.trim().split(" ").filter { it.isNotBlank() }.map { it.toInt() } }

        val amount = ownNumbers.count { it in winningNumbers }

        for(card in cardNumber + 1..cardNumber + amount) {
            cards[card]?.modify { it + cards[cardNumber]!!.value }
        }

    }

    writeValue(cards.values.sumOf { it.value })
}