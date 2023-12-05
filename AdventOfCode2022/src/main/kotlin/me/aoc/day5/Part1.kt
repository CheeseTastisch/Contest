package me.aoc.day5

import me.task.Task
import me.task.source.standard.file
import me.task.source.standard.string

fun main() = Task({
    string(
        """
            [D]    
        [N] [C]    
        [Z] [M] [P]
         1   2   3 
        
        move 1 from 2 to 1
        move 3 from 1 to 3
        move 2 from 2 to 1
        move 1 from 1 to 2
    """.trimIndent()
    ) {
        expected = "CMZ"
    }
    file("AdventOfCode2022/in/day5/part1.in")
}) {
    val stacks = mutableMapOf<Int, MutableList<Char>>()

    while (hasNextLine()) {
        val row = readValues().joinToString(" ")
        if (row.any { it in '0'..'9' }) break

        var currentStack = 1

        row.split("]")
            .filter { it.isNotBlank() }
            .forEach { column ->
                val crate = column.filter { it in 'A'..'Z' }[0]
                val leadingSpaces = column.count { it == ' ' } / 4
                currentStack += leadingSpaces

                stacks.getOrPut(currentStack) { mutableListOf() }.add(0, crate)
                currentStack++
            }
    }
    readValues()

    while(hasNextLine()) {
        val statement = readValues()

        val amount = statement[1].toInt()
        val from = statement[3].toInt()
        val to = statement[5].toInt()

        for (i in 1..amount) {
            val crate = stacks[from]!!.removeLast()
            stacks[to]!!.add(crate)
        }
    }

    writeValue(stacks.toList().sortedBy { it.first }.map { it.second.last() }.joinToString(""))
}