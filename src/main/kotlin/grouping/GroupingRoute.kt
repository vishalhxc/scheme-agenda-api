package grouping

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.groupingRoute() {
    route("/groupings") {
        post {
            call.receive<Grouping>().let { grouping ->
                call.respond(grouping.save())
            }
        }
    }
}