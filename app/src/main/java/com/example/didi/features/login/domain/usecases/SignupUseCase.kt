package com.example.didi.features.login.domain.usecases

import com.example.didi.features.login.domain.entities.AuthResult
import com.example.didi.features.login.domain.repositories.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        fullName: String?,
        email: String?,
        password: String?
    ): Result<AuthResult> { // Cambiado AuthSession por AuthResult
        return try {
            val name = fullName?.trim().orEmpty()
            val e = email?.trim().orEmpty()
            val p = password.orEmpty()

            if (name.isBlank() || e.isBlank() || p.isBlank()) {
                return Result.failure(Exception("Nombre, email y password son obligatorios"))
            }
            if (name.length < 2) {
                return Result.failure(Exception("Nombre demasiado corto"))
            }
            if (!e.contains("@") || !e.contains(".")) {
                return Result.failure(Exception("Email inválido"))
            }
            if (p.length < 6) {
                return Result.failure(Exception("El password debe tener al menos 6 caracteres"))
            }

            // Usamos signUp que es el nombre en tu interfaz AuthRepository
            val result = repository.signUp(fullName = name, email = e, password = p)

            if (result.accessToken.isBlank()) {
                Result.failure(Exception("Token inválido"))
            } else {
                Result.success(result)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}