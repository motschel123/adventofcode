plugins {
    kotlin("jvm") version "2.1.21"
}

group = ""
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets {
    main {
        kotlin.srcDir("src")  // Set source directory to `src`
        resources.srcDir("resources")  // If you have resources, you can configure them here
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:multik-core:0.2.3")
    implementation("org.jetbrains.kotlinx:multik-default:0.2.3")
}

kotlin {
    jvmToolchain(23)
}