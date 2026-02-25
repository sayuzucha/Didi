package com.example.didi.core.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @RidesRetrofit
    fun provideRidesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/api/v1/") // Para emulador Android
            // Si usas dispositivo físico: usa tu IP local
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}