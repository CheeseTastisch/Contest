package me.goldentrio.classic

import me.task.Task
import me.task.source.standard.directory
import me.task.source.standard.file

fun main() = Task({
    file("CCC2024Spring/classic/lvl3/level3_example.in") {
        expected("CCC2024Spring/classic/lvl3/level3_example.out")
    }
//    directory("CCC2024Spring/classic/lvl3") {
//        expected("level3_example.in", "level3_example.out")
//    }
}) {

}