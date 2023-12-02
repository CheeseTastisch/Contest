package me.task.util.extention

import kotlin.io.path.Path

/**
 * Gets a [Path] object from this string.
 */
val String.path
    get() = Path(this)

/**
 * Creates a random string with the given [length].
 */
fun String.Companion.random(length: Int = 8): String {
    val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..length)
        .map { source.random() }
        .joinToString("")
}