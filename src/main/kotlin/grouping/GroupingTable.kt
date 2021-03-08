package grouping

import org.jetbrains.exposed.sql.Table

object GroupingTable : Table() {
    val groupingId = uuid("grouping_id").autoGenerate()
    val name = text("name")
    val userId = uuid("user_id")
    val color = text("color")
    override val primaryKey: PrimaryKey = PrimaryKey(groupingId)
}