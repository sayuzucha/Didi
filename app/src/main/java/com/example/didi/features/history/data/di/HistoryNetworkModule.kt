package com.example.didi.features.history.data.di

import com.example.didi.core.di.RidesRetrofit
import com.example.didi.features.history.data.datasources.remote.api.HistoryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HistoryNetworkModule {

    @Provides
    @Singleton
    fun provideHistoryApi(
        @RidesRetrofit retrofit: Retrofit
    ): HistoryApi = retrofit.create(HistoryApi::class.java)
}
