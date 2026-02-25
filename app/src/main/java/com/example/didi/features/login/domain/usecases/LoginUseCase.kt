package com.example.didi.features.login.domain.usecases

import com.example.didi.features.login.domain.entities.AuthResult
import com.example.didi.features.login.domain.repositories.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String?,
        password: String?
    ): Result<AuthResult> {
        return try {
            val e = email?.trim().orEmpty()
            val p = password.orEmpty()

            if (e.isBlank() || p.isBlank()) {
                return Result.failure(Exception("Email y password son requeridos"))
            }

            val result = repository.login(email = e, password = p)

            if (result.accessToken.isBlank()) {
                Result.failure(Exception("Error al obtener acceso"))
            } else {
                Result.success(result)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}