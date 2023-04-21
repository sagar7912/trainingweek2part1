plugins {
    kotlin("jvm") version "1.8.0"
    application
    kotlin("plugin.serialization") version "1.8.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.glassfish.grizzly:grizzly-http-server:2.4.4")
    implementation("org.json:json:20220320")
    implementation("org.glassfish.jersey.core:jersey-server:2.34")
    implementation("org.glassfish.jersey.media:jersey-media-json-jackson:2.34")
    implementation("org.glassfish.jersey.inject:jersey-hk2:2.34")
    implementation("org.glassfish.jersey.containers:jersey-container-grizzly2-http:2.34")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.0")


    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}