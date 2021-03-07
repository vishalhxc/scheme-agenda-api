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
    fun String.version(): String = properties["dependency.version.$this"].toString()
    implementation("io.ktor:ktor-server-core:${"ktor".version()}")
    implementation("io.ktor:ktor-server-netty:${"ktor".version()}")
    implementation("io.ktor:ktor-serialization:${"ktor".version()}")
    implementation("io.ktor:ktor-server-tests:${"ktor".version()}")
    implementation("ch.qos.logback:logback-classic:${"logback".version()}")
    implementation("org.jetbrains.exposed:exposed-core:${"exposed".version()}")
    implementation("org.jetbrains.exposed:exposed-dao:${"exposed".version()}")
    implementation("org.jetbrains.exposed:exposed-jdbc:${"exposed".version()}")
    runtimeOnly("org.postgresql:postgresql:${"postgresql".version()}")
    testImplementation("io.kotest:kotest-runner-junit5:${"kotest".version()}")
    testImplementation("io.kotest:kotest-assertions-core:${"kotest".version()}")
    testImplementation("io.kotest:kotest-property:${"kotest".version()}")
    testImplementation("io.mockk:mockk:${"mockk".version()}")
}

tasks {
    build { dependsOn("flywayMigrate") }
    withType<KotlinCompile> { kotlinOptions.jvmTarget = "11" }
    withType<Test> { useJUnitPlatform() }
}

flyway {
    url = "jdbc:postgresql://localhost:5432/scheme_agenda_db"
    user = "scheme_admin"
    password = "schemeadmin"
    schemas = arrayOf("agendas_schema")
}

application { mainClass.set("ApplicationKt") }