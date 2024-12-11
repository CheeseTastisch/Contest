package me.aoc.day11

import me.task.Task
import me.task.source.standard.inferFile
import me.task.source.standard.string
import kotlin.time.measureTime

fun main() = Task({
    string("125 17") {
        expected = "55312"
    }

    inferFile("day11.in") {
        outExtension = "part1"
    }
}){
    println(measureTime {
        val stones = readLongs().map { Stone(it) }.toMutableList()

        for (i in 0..<25) {
            val newStones = mutableListOf<Stone>()
            for (stone in stones) {
                val newStone = stone.doAction()
                if (newStone != null) newStones.add(newStone)
            }

            stones.addAll(newStones)
        }

        writeValue(stones.size)
    })
}

data class Stone(var value: Long) {

    fun doAction(): Stone? {
        if (value == 0L) {
            value = 1
            return null
        }

        val digits = value.toString().count()
        if (digits % 2 == 0) {
            val leftValue = value.toString().substring(0, digits / 2).toLong()
            val rightValue = value.toString().substring(digits / 2).toLong()

            value = leftValue
            return Stone(rightValue)
        }

        value *= 2024
        return null
    }

}