package me.goldenduo

import me.task.Task
import me.task.source.standard.directory
import me.task.source.standard.file

fun main() = Task({
//    file("CCC2024Autumn/in/lvl1/level1_example.in") {
//        expected("CCC2024Autumn/in/lvl1/level1_example.out")
//    }
    directory("CCC2024Autumn/in/lvl1") {
        expected("level1_example.in", "level1_example.out")
    }
}){
    readValues()

    while (hasNextLine()) {
        val (x, y) = readInts()

        val xDesks = x / 3
        writeValue(xDesks * y, lineBreak = true)
    }
}