package me.goldentrio

import me.goldentrio.implementation.Contest as ContestImplementation
import me.goldentrio.implementation.Configuration as ConfigurationImplementation

object Contest {

    operator fun invoke(config: Configuration.() -> Unit, solve: IO.() -> Unit) =
        ContestImplementation(ConfigurationImplementation().apply(config)).call(solve)

}