package me.aoc.day6

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string
import me.task.util.extention.distinct

fun main() = Task({
    string("mjqjpqmgbljsphdztnvjfqwrcgsmlb") { expected = "19" }
    string("bvwbjplbgvbhsrlpgdmjqwftvncz") { expected = "23" }
    string("nppdvjthqldpwncqszvftbrmjlhg") { expected = "23" }
    string("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") { expected = "29" }
    string("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") { expected = "26" }
    file("AdventOfCode2022/in/day6/part2.in")
}) {
    val line = readValue()
    val currentSequence = StringBuilder(line.take(14))

    for (i in 14..<line.length) {
        if (currentSequence.toString().distinct().length == 14) {
            writeValue(i)
            break
        }

        currentSequence.deleteCharAt(0)
        currentSequence.append(line[i])
    }
}