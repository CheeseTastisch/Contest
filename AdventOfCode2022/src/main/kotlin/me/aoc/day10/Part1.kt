package me.aoc.day10

import me.task.Task
import me.task.source.standard.file

fun main() = Task({
    file("AdventOfCode2022/in/day10/example.part1.in") {
        expected("AdventOfCode2022/in/day10/example.part1.out")
    }
    file("AdventOfCode2022/in/day10/part1.in")
}){
    var currentX = 1
    val xHistory = mutableListOf<Int>()

    while (hasNextLine()) {
        val statement = readValue()

        when(statement) {
            "noop" -> xHistory.add(currentX)
            "addx" -> {
                xHistory.add(currentX)
                xHistory.add(currentX)
                currentX += readInt()
            }
        }

        readValues()
    }

    var value = 0
    for(i in 20..<xHistory.size step 40) {
        value += xHistory[i - 1] * i
    }

    writeValue(value)
}