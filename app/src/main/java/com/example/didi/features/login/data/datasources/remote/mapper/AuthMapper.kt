package com.example.didi.features.login.data.datasources.remote.mapper

import com.example.didi.features.login.data.datasources.remote.models.*
import com.example.didi.features.login.domain.entities.*

/* ===========================
   USER MAPPER
   =========================== */

fun UserDto.toDomain(): User {
    return User(
        id = this.id,
        fullName = this.fullName,
        email = this.email
    )
}

/* ===========================
   SIGNUP / LOGIN MAPPER
   =========================== */

// Mapeamos SignupResponseDto a la entidad de dominio AuthResult
fun SignupResponseDto.toDomain(): AuthResult {
    return AuthResult(
        accessToken = this.accessToken,
        tokenType = this.tokenType,
        user = this.user.toDomain() // Reutilizamos el mapper de UserDto
    )
}

// Mapeamos LoginResponseDto a la misma entidad AuthResult
fun LoginResponseDto.toDomain(): AuthResult {
    return AuthResult(
        accessToken = this.accessToken,
        tokenType = this.tokenType,
        user = this.user.toDomain()
    )
}