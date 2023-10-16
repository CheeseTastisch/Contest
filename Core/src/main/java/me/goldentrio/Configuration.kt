package me.goldentrio

import java.io.File
import java.nio.file.Path
import java.util.*

/**
 * The [Configuration] is used to configure from where the input for a contest task should be read.
 */
interface Configuration {

    /**
     * Adds the given [String] to the input.
     *
     * This will split the [String] on
     * - line breaks and
     * -spaces,
     * to create a two-dimensional [Deque] of [String]s.
     */
    operator fun String.unaryPlus()

    /**
     * Adds the given [File] to the input.
     *
     * This will read the [File] and split it on
     * - line breaks and
     * - spaces,
     * to create a two-dimensional [Deque] of [String]s.
     */
    operator fun File.unaryPlus()

    /**
     * Adds the given [Path] to the input.
     *
     * @see File.unaryPlus
     */
    operator fun Path.unaryPlus() = this.toFile().unaryPlus()

}