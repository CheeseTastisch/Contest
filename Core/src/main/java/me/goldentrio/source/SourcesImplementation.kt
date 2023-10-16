package me.goldentrio.source

internal class SourcesImplementation : Sources {

    private val _sources = mutableListOf<Source>()

    override val sources: List<Source>
        get() = _sources.toList()

    override fun Source.unaryPlus() {
        _sources += this
    }
}