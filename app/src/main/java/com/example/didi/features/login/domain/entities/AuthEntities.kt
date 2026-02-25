package com.example.didi.features.login.domain.entities

data class User(
    val id: Int,
    val fullName: String,
    val email: String
)

data class AuthSession(
    val accessToken: String,
    val tokenType: String,
    val user: User
)