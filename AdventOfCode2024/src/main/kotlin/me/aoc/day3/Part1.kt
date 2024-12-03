package me.aoc.day3

import me.task.Task
import me.task.source.standard.inferFile
import me.task.source.standard.string

fun main() = Task({
    string("""
        xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+
        mul(32,64]then(mul(11,8)mul(8,5))
    """.trimIndent()) {
        expected = "161"
    }

    inferFile("day3.in") {
        outExtension = "part1"
    }
}) {
    writeValue(
        Regex("mul\\((\\d+),(\\d+)\\)").findAll(readAllValues().joinToString(""))
            .map { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
            .sum()
    )
}