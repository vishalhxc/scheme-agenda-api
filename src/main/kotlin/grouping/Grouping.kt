package grouping

import error.AgendaError
import error.ErrorMessage
import io.ktor.http.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import serialization.UUIDSerializer
import java.util.*

@Serializable
data class Grouping(
    @Serializable(with = UUIDSerializer::class)
    val groupingId: UUID? = null,
    val name: String = "",
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID? = null,
    val color: String = ""
) {
    fun save(): Grouping = let { grouping ->
        transaction {
            GroupingTable.insert {
                it[name] = grouping.name
                it[userId] = grouping.userId ?: throw Exception("User ID is null.")
                it[color] = grouping.color
            } get GroupingTable.groupingId
        }.let { copy(groupingId = it) }
    }

    fun validate(): AgendaError? = mutableMapOf<String, String>().let { errors ->
        if (name.isBlank() || name.length > 50)
            errors["name"] = "Name is required and must be less than 50 characters."
        if (!color.matches(Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})\$")))
            errors["color"] = "Color must be a valid hex color code."
        if (userId == null)
            errors["userId"] = "Valid User ID is required."

        errors.ifEmpty { return null }

        return AgendaError(
            status = HttpStatusCode.BadRequest,
            message = ErrorMessage.BadRequest,
            fieldErrors = errors
        )
    }
}