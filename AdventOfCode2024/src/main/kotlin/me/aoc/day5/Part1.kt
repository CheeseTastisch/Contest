package me.aoc.day5

import me.task.Task
import me.task.source.standard.inferFile
import me.task.source.standard.string

fun main() = Task({
    string("""
        47|53
        97|13
        97|61
        97|47
        75|29
        61|13
        75|53
        29|13
        97|29
        53|29
        61|53
        97|53
        61|29
        47|13
        75|47
        97|75
        47|61
        75|61
        47|29
        75|13
        53|13

        75,47,61,53,29
        97,61,53,29,13
        75,29,13
        75,97,47,61,53
        61,13,29
        97,13,75,29,47
    """.trimIndent()) {
        expected = "143"
    }

    inferFile("day5.in") {
        outExtension = "part1"
    }
}) {
    val orderingRules = mutableListOf<Pair<Int, Int>>()
    val pages = mutableListOf<List<Int>>()

    while (hasNextLine()) {
        val value = readValue(endOfLine = true)
        if (value.isBlank()) continue

        if (value.contains("|")) orderingRules.add(value.split("|").map { it.toInt() }.let { it[0] to it[1] })
        else pages.add(value.split(",").map { it.toInt() })
    }

    val allowed = pages.filter { it.isCorrectlyOrdered(orderingRules) }
    writeValue(allowed.sumOf { it.middle() })
}

fun List<Int>.isCorrectlyOrdered(rules: List<Pair<Int, Int>>, print: Boolean = false): Boolean {
    val allDisallowed = mutableSetOf<Int>()

    for (item in this) {
        if (item in allDisallowed) {
            if (print) println(item)
            return false
        }
        allDisallowed.addAll(rules.filter { it.second == item }.map { it.first })
    }

    if (print) println("All good")
    return true
}

fun List<Int>.middle() = this.let { it[it.size / 2] }