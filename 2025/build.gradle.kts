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
}

kotlin {
    jvmToolchain(23)
}