package com.poulastaa.data.model.auth

import io.ktor.server.auth.*
import kotlinx.serialization.Serializable

@Serializable
data class UserSession(
    val name: String,
    val email: String,
) : Principal
