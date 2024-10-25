package me.goldenduo

import me.task.Task
import me.task.source.standard.directory
import me.task.source.standard.file

fun main() = Task({
//    file("CCC2024Autumn/in/lvl2/level2_example.in") {
//        expected("CCC2024Autumn/in/lvl2/level2_example.out")
//    }
    directory("CCC2024Autumn/in/lvl2") {
        expected("level2_example.in", "level2_example.out")
    }
}){
    readValues()

    while (hasNextLine()) {
        val (x, y) = readInts()

        val xDesks = x / 3
        var id = 1

        for(i in 0..<y) {
            for (j in 0..<xDesks) {
                writeValue(id, id, id)
                id++
            }

            writeValue(lineBreak = true)
        }

        writeValue(lineBreak = true)
    }
}