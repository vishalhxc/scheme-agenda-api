package error

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.justRun
import io.mockk.verify
import serialization.ErrorMessageSerializer

class ErrorMessageTest : FunSpec({

    test("from text, bad request returns bad request message") {
        ErrorMessage.fromText("Bad request: there are errors in the request.") shouldBe ErrorMessage.BadRequest
    }

    test("from text, not found returns not found message") {
        ErrorMessage.fromText("Not found: resource not found.") shouldBe ErrorMessage.NotFound
    }

    test("from text, ise returns ise message") {
        ErrorMessage.fromText("Internal server error: there was an error processing the request.") shouldBe ErrorMessage.InternalServerError
    }

    test("from text, conflict returns conflict message") {
        ErrorMessage.fromText("Conflict: resource already exists.") shouldBe ErrorMessage.Conflict
    }

    test("from text, empty returns empty message") {
        ErrorMessage.fromText("") shouldBe ErrorMessage.Empty
    }

    test("to string, conflict returns conflict message") {
        ErrorMessage.Conflict.toString() shouldBe "Conflict: resource already exists."
    }

    test("to string, empty returns empty string") {
        ErrorMessage.Empty.toString() shouldBe ""
    }
})
