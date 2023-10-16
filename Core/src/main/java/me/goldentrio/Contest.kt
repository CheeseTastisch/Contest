package me.goldentrio

import me.goldentrio.io.IO
import me.goldentrio.io.IOImplementation
import me.goldentrio.source.Sources
import me.goldentrio.source.SourcesImplementation

/**
 * The [Contest] object is the main entry point for the library.
 */
object Contest {

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