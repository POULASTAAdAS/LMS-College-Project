package com.poulastaa.domain.repository

import com.poulastaa.data.model.table.session.SessionStorageTable
import com.poulastaa.domain.dao.session.SessionStorageDB
import com.poulastaa.plugins.dbQuery
import io.ktor.server.sessions.*

class SessionStorageDatabaseImpl : SessionStorage {
    override suspend fun write(id: String, value: String) {
        val session = dbQuery {
            SessionStorageDB.find {
                SessionStorageTable.sessionId eq id
            }.firstOrNull()
        }

        if (session != null) dbQuery {
            session.value = value
        }
        else {
            dbQuery {
                SessionStorageDB.new {
                    this.sessionId = id
                    this.value = value
                }
            }
        }
    }


    override suspend fun read(id: String): String {
        return dbQuery {
            SessionStorageDB.find {
                SessionStorageTable.sessionId eq id
            }.firstOrNull()?.value ?: throw NoSuchElementException("Session $id not found")
        }
    }

    override suspend fun invalidate(id: String) {
        dbQuery {
            SessionStorageDB.find {
                SessionStorageTable.sessionId eq id
            }.firstOrNull()?.delete()
        }
    }
}