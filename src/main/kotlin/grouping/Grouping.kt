package grouping

import grouping.db.GroupingTable
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import utilities.UUIDSerializer
import java.util.*

@Serializable
data class Grouping(
    @Serializable(with = UUIDSerializer::class)
    val groupingId: UUID? = null,
    val name: String,
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val color: String = ""
) {
    fun save(): Grouping = let { grouping ->
        transaction {
            GroupingTable.insert {
                it[name] = grouping.name
                it[userId] = grouping.userId
                it[color] = grouping.color
            } get GroupingTable.groupingId
        }.let { copy(groupingId = it) }
    }
}