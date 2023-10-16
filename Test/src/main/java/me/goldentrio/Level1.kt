package me.goldentrio

import me.goldentrio.source.standard.directory

fun main() = Contest({
    directory("Test/testin") {
        expected("a", "Test/testin/output.out")
    }
}) {
    writeInt(readInts().sum())
    writeLine(readLine().joinToString("-"))
    writeBreak()
    writeBreak()

    if (hasNextLine()) readLine()

    repeat()
}