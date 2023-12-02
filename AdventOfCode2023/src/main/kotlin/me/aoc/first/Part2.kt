package me.aoc.first

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string("""
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
    """.trimIndent()) {
        expected = "281"
    }
    file("AdventOfCode2023/in/first/part2.in")
}) {
    var value = 0
    while (hasNextLine()) {
        val line = readValue(endOfLine = true)

        var firstDigitIndex = line.indexOfFirst { it.isDigit() }
        var lastDigitIndex = line.indexOfLast { it.isDigit() }

        var firstDigit = if (firstDigitIndex == -1) 0 else line[firstDigitIndex].toString().toInt()
        var lastDigit = if (lastDigitIndex == -1) 0 else line[lastDigitIndex].toString().toInt()

        val pattern = Regex("(?=(one|two|three|four|five|six|seven|eight|nine))")

        val matches = pattern.findAll(line)

        matches.forEach {
            val digit = when (it.groups[1]!!.value) {
                "one" -> 1
                "two" -> 2
                "three" -> 3
                "four" -> 4
                "five" -> 5
                "six" -> 6
                "seven" -> 7
                "eight" -> 8
                "nine" -> 9
                else -> 0
            }

            if (it.groups[1]!!.range.first < firstDigitIndex || firstDigitIndex == -1) {
                firstDigit = digit
                firstDigitIndex = it.groups[0]!!.range.first
            }

            if (it.groups[1]!!.range.last > lastDigitIndex || lastDigitIndex == -1) {
                lastDigit = digit
                lastDigitIndex = it.groups[0]!!.range.last
            }
        }

        value += firstDigit * 10 + lastDigit
    }
    writeValue(value)
}