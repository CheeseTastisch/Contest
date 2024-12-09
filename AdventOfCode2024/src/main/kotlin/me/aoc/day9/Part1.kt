package me.aoc.day9

import me.task.Task
import me.task.source.standard.inferFile
import me.task.source.standard.string

fun main() = Task({
    string(
        """
        2333133121414131402
    """.trimIndent()
    ) {
        expected = "1928"
    }

    inferFile("day9.in") {
        outExtension = "part1"
    }
}) {
    val blocks = mutableListOf<Block>()
    for ((index, value) in readValue().withIndex()) {
        val block = if (index % 2 == 0) Number(index / 2) else Empty
        for (i in 0..<value.toString().toInt()) blocks.add(block)
    }

    while (!blocks.isSorted()) {
        blocks.swap()
    }

    writeValue(blocks.checkSum())
}

fun List<Block>.isSorted(): Boolean {
    val lastNumber = this.indexOfLast { it is Number }
    val firstEmpty = this.indexOfFirst { it is Empty }
    return lastNumber + 1 == firstEmpty
}

fun MutableList<Block>.swap() {
    val firstEmpty = this.indexOfFirst { it is Empty }
    val lastNumber = this.indexOfLast { it is Number }

    val temp = this[firstEmpty]
    this[firstEmpty] = this[lastNumber]
    this[lastNumber] = temp
}

fun List<Block>.checkSum() =
    this.foldIndexed(0L) { index, acc, block ->
        if (block is Number) {
            acc + (index * block.value)
        } else {
            acc
        }
    }

sealed interface Block

data object Empty : Block {

    override fun toString(): String = "."

}

data class Number(val value: Int) : Block {

    override fun toString(): String = value.toString()

}