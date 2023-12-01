pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "Contest"
include("Core")
include("CCC2023Autumn")
include("CatCoderTraining")
include("AdventOfCode2023")
