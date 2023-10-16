package me.goldentrio.extention

import kotlin.io.path.Path

val String.path
    get() = Path(this)