package com.example.didi.features.login.domain.entities

data class AuthResult(
    val accessToken: String,
    val tokenType: String,
    val user: User
)

data class User(
    val id: Int,
    val fullName: String,
    val email: String
)