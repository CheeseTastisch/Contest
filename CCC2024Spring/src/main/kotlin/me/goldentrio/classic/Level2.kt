package me.goldentrio.classic

import me.task.Task
import me.task.source.standard.directory
import me.task.source.standard.file

fun main() = Task({
    file("CCC2024Spring/classic/lvl2/level2_example.in") {
        expected("CCC2024Spring/classic/lvl2/level2_example.out")
    }
//    directory("CCC2024Spring/classic/lvl2") {
//        expected("level2_example.in", "level2_example.out")
//    }
}) {

}