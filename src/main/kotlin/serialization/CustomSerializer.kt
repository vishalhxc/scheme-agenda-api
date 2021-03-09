package serialization

import error.ErrorMessage
import io.ktor.http.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

@Serializer(forClass = UUID::class)
object UUIDSerializer : KSerializer<UUID?> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): UUID? =
        runCatching { UUID.fromString(decoder.decodeString()) }.getOrNull()

    override fun serialize(encoder: Encoder, value: UUID?) =
        encoder.encodeString(value?.toString() ?: "")
}

@Serializer(forClass = HttpStatusCode::class)
object HttpStatusCodeSerializer : KSerializer<HttpStatusCode> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("HttpStatusCode", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): HttpStatusCode =
        HttpStatusCode.fromValue(decoder.decodeInt())

    override fun serialize(encoder: Encoder, value: HttpStatusCode) =
        encoder.encodeInt(value.value)
}

@Serializer(forClass = ErrorMessage::class)
object ErrorMessageSerializer : KSerializer<ErrorMessage> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("ErrorMessage", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ErrorMessage =
        ErrorMessage.fromText(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: ErrorMessage) =
        encoder.encodeString(value.text)
}