package me.aoc.day5

import me.task.Task
import me.task.source.standard.inferFile
import me.task.source.standard.string

fun main() = Task({
    string(
        """
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
    """.trimIndent()
    ) {
        expected = "123"
    }

    inferFile("day5.in") {
        outExtension = "part2"
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

    writeValue(pages
        .filter { !it.isCorrectlyOrdered(orderingRules) }
        .map { it.sortedWith(Comparing(orderingRules, it)) }
        .sumOf { it.middle() }
    )
}

class Comparing(rules: List<Pair<Int, Int>>, private val pageNumbers: List<Int>) : Comparator<Int> {

    private val actuallyRules = rules.filter { it.first in pageNumbers && it.second in pageNumbers }

    override fun compare(o1: Int?, o2: Int?): Int {
        if (o1 == null || o2 == null) return 0
        if (o1 == o2) return 0

        val visited = mutableSetOf(o1 to emptyList<Int>())
        val queue = mutableListOf(o1)

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (current == o2) return -1

            actuallyRules.filter { it.first == current }.forEach {
                if (visited.none { visit -> visit.first == it.second } && it.second !in queue) {
                    visited.add(it.second to (visited.first { visit -> visit.first == it.first }.second + it.first))
                    queue.add(it.second)
                }
            }
        }

        return 1
    }
}