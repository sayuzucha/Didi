package com.example.didi.features.ride_in_progress.data.di

import com.example.didi.features.ride_in_progress.data.datasources.remote.api.RideWsClient
import com.example.didi.features.ride_in_progress.data.repositories.RideInProgressRepositoryImpl
import com.example.didi.features.ride_in_progress.domain.repositories.RideInProgressRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RideInProgressModule {

    @Provides
    @Singleton
    fun provideRideWsClient(okHttpClient: OkHttpClient): RideWsClient {
        return RideWsClient(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideRideInProgressRepository(wsClient: RideWsClient): RideInProgressRepository {
        return RideInProgressRepositoryImpl(wsClient)
    }
}