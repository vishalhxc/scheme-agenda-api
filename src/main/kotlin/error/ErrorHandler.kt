package error

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun StatusPages.Configuration.handleErrors() {
    exception<Throwable> {
        val code = HttpStatusCode.InternalServerError
        call.respond(code, AgendaError(code, ErrorMessage.InternalServerError))
    }
}