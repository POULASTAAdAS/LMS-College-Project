package com.poulastaa.data.model.table.teacher

import com.poulastaa.data.model.table.address.TeacherDetailsTable
import com.poulastaa.data.model.table.address.TeacherDetailsTable.default
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object TeacherTable : IntIdTable() {
    val email = varchar("email", 255).uniqueIndex()
    val teacherTypeId = reference("teacherTypeId", TeacherTypeTable.id, onDelete = ReferenceOption.CASCADE)
    val emailVerified = TeacherDetailsTable.bool("emailVerified").default(false)
}