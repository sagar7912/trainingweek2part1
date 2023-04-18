plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.glassfish.grizzly:grizzly-http-server:2.4.4")
    implementation("org.json:json:20200518")
    implementation("org.glassfish.jersey.core:jersey-server:2.34")
    implementation("org.glassfish.jersey.media:jersey-media-json-jackson:2.34")
    implementation("org.glassfish.jersey.inject:jersey-hk2:2.34")
    implementation("org.glassfish.jersey.containers:jersey-container-grizzly2-http:2.34")


    testImplementation(kotlin("test"))
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