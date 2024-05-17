package com.poulastaa.utils

import com.poulastaa.data.model.table.utils.PrincipalTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Principal(id :EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<Principal>(PrincipalTable)

    val name by PrincipalTable.name
    val email by PrincipalTable.email
}