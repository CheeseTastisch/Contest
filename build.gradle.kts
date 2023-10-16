import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
}

group = "me.golendtrio"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

allprojects {
    apply(plugin = "kotlin")

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