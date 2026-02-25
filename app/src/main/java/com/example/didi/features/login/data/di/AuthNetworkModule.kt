package com.example.didi.features.login.data.di


import com.example.didi.core.di.RidesRetrofit
import com.example.didi.features.login.data.datasources.remote.api.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthNetworkModule {

    @Provides
    @Singleton
    fun provideAuthApi(
        @RidesRetrofit retrofit: Retrofit
    ): AuthApi = retrofit.create(AuthApi::class.java)
}