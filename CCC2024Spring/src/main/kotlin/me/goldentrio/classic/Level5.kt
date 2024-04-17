package me.goldentrio.classic

import me.task.Task
import me.task.source.standard.directory
import me.task.source.standard.file

fun main() = Task({
    file("CCC2024Spring/classic/lvl5/level5_example.in") {
        expected("CCC2024Spring/classic/lvl5/level5_example.out")
    }
//    directory("CCC2024Spring/classic/lvl5") {
//        expected("level5_example.in", "level5_example.out")
//    }
}) {

}