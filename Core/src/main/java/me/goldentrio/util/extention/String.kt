package me.goldentrio.util.extention

import kotlin.io.path.Path

val String.path
    get() = Path(this)

fun String.Companion.random(length: Int = 8): String {
    val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..length)
        .map { source.random() }
        .joinToString("")
}