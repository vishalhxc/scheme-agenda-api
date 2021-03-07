import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.routing.route
import io.ktor.routing.post

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() = routing {
    route("/groupings") {
        post { call.respond(status = HttpStatusCode.Created, "Yo!") }
    }
}