package com.poulastaa.di

import com.poulastaa.data.repository.JWTRepository
import com.poulastaa.domain.repository.JWTRepositoryImpl
import io.ktor.server.application.*
import org.koin.dsl.module

fun provideJWTRepo(call: Application) = module {
    single<JWTRepository> {
        JWTRepositoryImpl(call)
    }
}