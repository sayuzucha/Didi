package com.example.didi.features.login.data.repositories

import android.util.Log
import com.example.didi.features.login.data.datasources.remote.api.AuthApi
import com.example.didi.features.login.data.datasources.remote.mapper.toDomain
import com.example.didi.features.login.data.datasources.remote.models.LoginRequestDto
import com.example.didi.features.login.data.datasources.remote.models.SignupRequestDto
import com.example.didi.features.login.domain.entities.AuthResult
import com.example.didi.features.login.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : AuthRepository {

    override suspend fun login(email: String, password: String): AuthResult {
        return try {
            val response = api.login(
                LoginRequestDto(email = email, password = password)
            )

            val body = response.body()
            if (response.isSuccessful && body != null) {
                Log.d("AuthRepository", "Login exitoso para: ${body.user.email}")
                body.toDomain()
            } else {
                val errorMsg = "Error en login: ${response.code()} - ${response.errorBody()?.string()}"
                Log.e("AuthRepository", errorMsg)
                throw Exception(errorMsg)
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Fallo en la conexión de Login", e)
            throw e
        }
    }

    override suspend fun signUp(fullName: String, email: String, password: String): AuthResult {
        return try {
            val response = api.signUp(
                SignupRequestDto(
                    fullName = fullName,
                    email = email,
                    password = password
                )
            )

            val body = response.body()
            if (response.isSuccessful && body != null) {
                Log.d("AuthRepository", "Registro exitoso para: ${body.user.fullName}")
                body.toDomain()
            } else {
                val errorMsg = "Error en registro: ${response.code()} - ${response.errorBody()?.string()}"
                Log.e("AuthRepository", errorMsg)
                throw Exception(errorMsg)
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Fallo en la conexión de Registro", e)
            throw e
        }
    }
}