package com.example.didi.features.home.data.di

import com.example.didi.core.di.RidesRetrofit
import com.example.didi.features.home.data.datasources.remote.api.RidesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeNetworkModule {

    @Provides
    @Singleton
    fun provideRidesApi(
        @RidesRetrofit retrofit: Retrofit
    ): RidesApi = retrofit.create(RidesApi::class.java)
}