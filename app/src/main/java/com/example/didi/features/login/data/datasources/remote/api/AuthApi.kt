package com.example.didi.features.login.data.datasources.remote.api


import com.example.didi.features.login.data.datasources.remote.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/signup")
    suspend fun signUp(
        @Body request: SignupRequestDto
    ): Response<SignupResponseDto>

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): Response<LoginResponseDto>
}