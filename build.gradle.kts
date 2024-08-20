import java.util.Date

plugins {
    kotlin("jvm") version "2.0.20-RC2"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
}

group = "io.github.lmfs.box"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.fusesource.jansi:jansi:2.4.1")
    implementation("com.github.ajalt.clikt:clikt:4.4.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<ProcessResources> {
    val resourceTargets = listOf("META-INF/lmfsbox.properties", "META-INF/lmfsbox.license.txt", "META-INF/lmfsbox.copyright.txt")
    val replaceProperties = mapOf(
        Pair("gradle", gradle),
        Pair("project", project),
        Pair("date", Date()),
        Pair("building", "Gradle v${gradle.gradleVersion} (JavaToolchain ${java.toolchain.languageVersion.get()}) on ${System.getProperty("os.arch")}"),
        Pair("licenseText", rootProject.file("LICENSE").readText()),
        Pair("copyrightText", rootProject.file("COPYRIGHT").readText())
    )
    filesMatching(resourceTargets) {
        expand(replaceProperties)
    }
}

application {
    mainClass.set("io.github.lmfs.box.LMFSBoxKt")
}
