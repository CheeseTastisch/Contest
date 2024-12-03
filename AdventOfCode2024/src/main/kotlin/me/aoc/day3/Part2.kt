package me.aoc.day3

import me.task.Task
import me.task.source.standard.inferFile
import me.task.source.standard.string

fun main() = Task({
    string("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))") {
        expected = "48"
    }

    inferFile("day3.in") {
        outExtension = "part2"
    }
}) {
    var enabled = true
    writeValue(
        Regex("(mul\\((?<a>\\d+),(?<b>\\d+)\\))|(?<ctrl>do(n't)?)").findAll(readAllValues().joinToString(""))
            .map {
                val ctrl = it.groups["ctrl"]?.value
                if (ctrl != null) {
                    enabled = ctrl == "do"
                    return@map 0
                }

                if (!enabled) return@map 0
                it.groups["a"]!!.value.toInt() * it.groups["b"]!!.value.toInt()
            }
            .sum()
    )
}