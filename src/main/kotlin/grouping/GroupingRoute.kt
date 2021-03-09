package grouping

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.groupingRoute() {
    route("/groupings") {
        post {
            call.receive<Grouping>().let { grouping ->
                grouping.validate()?.let { error -> call.respond(error.status, it) }
                call.respond(HttpStatusCode.Created, grouping.save())
            }
        }
    }
}