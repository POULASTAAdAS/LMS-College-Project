package online.poulastaa.domain.dao.util

import online.poulastaa.data.model.table.util.StatusTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Status(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Status>(StatusTable)

    val type by StatusTable.type
}