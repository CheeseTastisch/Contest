package me.task

import me.task.io.IO
import me.task.io.IOImplementation
import me.task.source.Sources
import me.task.source.SourcesImplementation

/**
 * The [Task] object is the main entry point for the library.
 */
object Task {

    /**
     * Tries to solve the given [sources] with the given [solve] function.
     */
    operator fun invoke(sources: Sources.() -> Unit, solve: IO.() -> Unit) {
        SourcesImplementation()
            .apply(sources)
            .sources
            .forEach {
                IOImplementation(it, solve)
            }
    }

}