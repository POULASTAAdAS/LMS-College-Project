package com.poulastaa.data.model.table.teacher

import com.poulastaa.data.model.table.address.AddressTypeTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object TeacherAddressTable : Table() {
    val teacherId = reference("teacherId", TeacherTable.id, onDelete = ReferenceOption.CASCADE)
    val addressTypeId = reference("addressTypeId", AddressTypeTable.id, onDelete = ReferenceOption.CASCADE)
    val houseNumb = varchar("houseNumb", 100)
    val street = varchar("street", 200)
    val city = varchar("city", 100).default("Kolkata")
    val zip = integer("zip") // todo change to string or will break
    val state = varchar("state", 100).default("West Bengal")
    val country = varchar("country", 100).default("India")

    override val primaryKey = PrimaryKey(teacherId, addressTypeId)
}