package error

import io.ktor.http.*
import kotlinx.serialization.Serializable
import serialization.HttpStatusCodeSerializer

@Serializable
data class AgendaError(
    @Serializable(with = HttpStatusCodeSerializer::class)
    val status: HttpStatusCode,
    val message: ErrorMessage,
    val fieldErrors: Map<String, String> = emptyMap()
)