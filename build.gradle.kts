import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
}

repositories {
    mavenCentral()
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")

    group = "me.golendtrio"
    version = "1.0"

    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}

configure(subprojects - project(":Core")) {
    dependencies {
        implementation(project(":Core"))
    }
}

kotlin {
    jvmToolchain(17)
}