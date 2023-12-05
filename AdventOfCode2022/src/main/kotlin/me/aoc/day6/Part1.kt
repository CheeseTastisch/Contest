package me.aoc.day6

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string
import me.task.util.extention.distinct

fun main() = Task({
    string("mjqjpqmgbljsphdztnvjfqwrcgsmlb") { expected = "7" }
    string("bvwbjplbgvbhsrlpgdmjqwftvncz") { expected = "5" }
    string("nppdvjthqldpwncqszvftbrmjlhg") { expected = "6" }
    string("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") { expected = "10" }
    string("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") { expected = "11" }
    file("AdventOfCode2022/in/day6/part1.in")
}) {
    val line = readValue()
    val currentSequence = StringBuilder(line.take(4))

    for (i in 4..<line.length) {
        if (currentSequence.toString().distinct().length == 4) {
            writeValue(i)
            break
        }

        currentSequence.deleteCharAt(0)
        currentSequence.append(line[i])
    }
}