package me.goldentrio

import me.goldentrio.extention.path

fun main() {
    Contest({
        +"Test/testin/".path
    }) {
        val (a, b, c) = readInts()
        val (d, e) = readDoubles()
        val first = readValue()
        val other = readLine()

        writeInt(a + b)
        writeLine("$c")
        writeDoubles(d + e)
        writeValue(first)
        writeValue(other.joinToString("-"))
        writeBreak()
        writeBreak()
    }
}