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
        expected = "2858"
    }

    inferFile("day9.in") {
        outExtension = "part2"
    }
}) {
    val blocks = mutableListOf<MultiBlock>()
    for ((index, value) in readValue().withIndex()) {
        val size = value.toString().toInt()
        blocks.add(if (index % 2 == 0) NumberMultiBlock(size, index / 2) else EmptyMultiBlock(size))
    }

    blocks.swap()

    writeValue(blocks.extend().checkSum())
}

fun List<MultiBlock>.getSwaps(): List<MultiBlock> {
    return this.filterIsInstance<NumberMultiBlock>().reversed()
}

fun MutableList<MultiBlock>.swap() {
    val swaps = this.getSwaps()

    for (swap in swaps) {
        val swapIndex = this.indexOf(swap)
        val firstFreeIndexed = this.withIndex().firstOrNull { (index, multiBlock) ->
            multiBlock is EmptyMultiBlock && multiBlock.size >= swap.size && index < swapIndex
        }

        if (firstFreeIndexed == null) continue

        val (freePosition, firstFree) = firstFreeIndexed

        if (firstFree.size == swap.size) {
            this[swapIndex] = firstFree
            this[freePosition] = swap
        } else {
            this[swapIndex] = EmptyMultiBlock(swap.size)
            this[freePosition] = swap
            this.add(freePosition + 1, EmptyMultiBlock(firstFree.size - swap.size))
        }
    }
}

fun List<MultiBlock>.extend(): List<Block> {
    val blocks = mutableListOf<Block>()
    for (multiBlock in this) {
        for (i in 0..<multiBlock.size) {
            blocks.add(multiBlock.toBlock())
        }
    }
    return blocks
}

sealed interface MultiBlock {

    val size: Int

    fun toBlock(): Block

}

data class EmptyMultiBlock(override val size: Int) : MultiBlock {

    override fun toBlock() = Empty

    override fun toString(): String = ".".repeat(size)

}

data class NumberMultiBlock(override val size: Int, val value: Int) : MultiBlock {

    private val block by lazy { Number(value) }

    override fun toBlock() = block

    override fun toString(): String = value.toString().repeat(size)

}