package serialization

import error.ErrorMessage
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ktor.http.*
import io.mockk.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

class CustomSerializerTest : FunSpec({
    val decoder: Decoder = mockk()
    val encoder: Encoder = mockk()

    afterTest { clearAllMocks() }

    test("deserialize uuid, uuid string maps to uuid type") {
        val uuid = UUID.randomUUID()
        every { decoder.decodeString() } returns uuid.toString()
        UUIDSerializer.deserialize(decoder) shouldBe uuid
        verify { decoder.decodeString() }
    }

    test("deserialize uuid, non-uuid string maps to null") {
        every { decoder.decodeString() } returns "some non uuid"
        UUIDSerializer.deserialize(decoder) shouldBe null
        verify { decoder.decodeString() }
    }

    test("serialize uuid, uuid maps to uuid string") {
        val uuid = UUID.randomUUID()
        justRun { encoder.encodeString(uuid.toString()) }
        UUIDSerializer.serialize(encoder, uuid)
        verify { encoder.encodeString(uuid.toString()) }
    }

    test("serialize uuid, null maps to empty string") {
        justRun { encoder.encodeString("") }
        UUIDSerializer.serialize(encoder, null)
        verify { encoder.encodeString("") }
    }

    test("deserialize http status code, int 409 maps to http status code conflict") {
        every { decoder.decodeInt() } returns 409
        HttpStatusCodeSerializer.deserialize(decoder) shouldBe HttpStatusCode.Conflict
        verify { decoder.decodeInt() }
    }

    test("deserialize http status code, int 201 maps to http status code created") {
        every { decoder.decodeInt() } returns 201
        HttpStatusCodeSerializer.deserialize(decoder) shouldBe HttpStatusCode.Created
        verify { decoder.decodeInt() }
    }

    test("deserialize http status code, int 500 maps to http status code internal server error") {
        every { decoder.decodeInt() } returns 500
        HttpStatusCodeSerializer.deserialize(decoder) shouldBe HttpStatusCode.InternalServerError
        verify { decoder.decodeInt() }
    }

    test("deserialize http status code, int 7000 returns unknown") {
        val code = 7000
        every { decoder.decodeInt() } returns code
        HttpStatusCodeSerializer.deserialize(decoder) shouldBe HttpStatusCode(code, "Unknown Status Code")
        verify { decoder.decodeInt() }
    }

    test("serialize http status code, http status code not found returns int 404") {
        val code = 404
        justRun { encoder.encodeInt(code) }
        HttpStatusCodeSerializer.serialize(encoder, HttpStatusCode.NotFound)
        verify { encoder.encodeInt(code) }
    }

    test("serialize http status code, http status code no content returns int 204") {
        val code = 204
        justRun { encoder.encodeInt(code) }
        HttpStatusCodeSerializer.serialize(encoder, HttpStatusCode.NoContent)
        verify { encoder.encodeInt(code) }
    }

    test("serialize http status code, http status code service unavailable returns int 503") {
        val code = 503
        justRun { encoder.encodeInt(code) }
        HttpStatusCodeSerializer.serialize(encoder, HttpStatusCode.ServiceUnavailable)
        verify { encoder.encodeInt(code) }
    }

    test("deserialize error message, not found message returns not found") {
        val message = "Not found: resource not found."
        every { decoder.decodeString() } returns message
        ErrorMessageSerializer.deserialize(decoder) shouldBe ErrorMessage.NotFound
        verify { decoder.decodeString() }
    }

    test("deserialize error message, random message returns empty") {
        val message = "Blah."
        every { decoder.decodeString() } returns message
        ErrorMessageSerializer.deserialize(decoder) shouldBe ErrorMessage.Empty
        verify { decoder.decodeString() }
    }

    test("serialize error message, conflict returns conflict message") {
        val message = "Conflict: resource already exists."
        justRun { encoder.encodeString(message) }
        ErrorMessageSerializer.serialize(encoder, ErrorMessage.Conflict)
        verify { encoder.encodeString(message) }
    }

    test("serialize error message, empty returns empty string") {
        val message = ""
        justRun { encoder.encodeString(message) }
        ErrorMessageSerializer.serialize(encoder, ErrorMessage.Empty)
        verify { encoder.encodeString(message) }
    }
})
