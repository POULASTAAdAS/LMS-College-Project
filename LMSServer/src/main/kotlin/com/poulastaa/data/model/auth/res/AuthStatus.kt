package com.poulastaa.data.model.auth.res

import kotlinx.serialization.Serializable

@Serializable
enum class AuthStatus {
    LOGIN,
    SIGNUP,
    EMAIL_NOT_REGISTERED,
    PRINCIPLE_FOUND,
    HEAD_CLARK_FOUND
}