package com.example.didi.features.login.domain.repositories

import com.example.didi.features.login.domain.entities.AuthResult

interface AuthRepository {

    suspend fun signUp(
        fullName: String,
        email: String,
        password: String
    ): AuthResult

    suspend fun login(
        email: String,
        password: String
    ): AuthResult
}