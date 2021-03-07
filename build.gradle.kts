import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.flywaydb.flyway") version "7.6.0"
    kotlin("jvm") version "1.4.31"
    application
}

group = "com.scheme"
version = "0.0.1"

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlinx") }
    maven { url = uri("https://dl.bintray.com/kotlin/ktor") }
}

dependencies {
    implementation("io.ktor:ktor-server-core:1.5.+")
    implementation("io.ktor:ktor-server-netty:1.5.+")
    implementation("ch.qos.logback:logback-classic:1.2.+")
    implementation("io.ktor:ktor-serialization:1.5.+")
    implementation("io.ktor:ktor-server-tests:1.5.+")
    testImplementation("io.kotest:kotest-runner-junit5:4.4.+")
    testImplementation("io.kotest:kotest-assertions-core:4.4.+")
    testImplementation("io.kotest:kotest-property:4.4.+")
    testImplementation("io.mockk:mockk:1.10.+")
}

tasks {
    withType<KotlinCompile> { kotlinOptions.jvmTarget = "11" }
    withType<Test> { useJUnitPlatform() }
}

application { mainClassName = "ApplicationKt" }