import grouping.groupingRoute
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.serialization.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.install() {
    HttpClient(CIO) { install(JsonFeature) { serializer = KotlinxSerializer() } }
    install(ContentNegotiation) { register(ContentType.Application.Json, SerializationConverter(DefaultJson)) }
}

fun Application.routes() = routing {
    groupingRoute()
}

fun Application.databaseConnection() = Database.connect(
    driver = "org.postgresql.Driver",
    url = environment.config.property("exposed.datasource.connectionString").getString(),
    user = environment.config.property("exposed.datasource.user").getString(),
    password = environment.config.property("exposed.datasource.password").getString()
)