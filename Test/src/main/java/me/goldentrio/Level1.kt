package me.goldentrio

import me.goldentrio.source.standard.file
import me.goldentrio.util.extention.path

fun main() {
    Contest({
        file("Test/testin/a.in".path) {
            expectedPath = "Test/testin/output.out".path
        }
    }) {
        writeInt(readInts().sum())
        writeLine(readLine().joinToString("-"))
        writeBreak()
        writeBreak()

        if (hasNextLine()) readLine()

        repeat()
    }
}