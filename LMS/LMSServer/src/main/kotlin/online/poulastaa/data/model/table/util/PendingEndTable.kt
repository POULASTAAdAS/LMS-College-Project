package online.poulastaa.data.model.table.util

import org.jetbrains.exposed.dao.id.IntIdTable

object PendingEndTable : IntIdTable() {
    val type = varchar("type", 400).uniqueIndex()
}