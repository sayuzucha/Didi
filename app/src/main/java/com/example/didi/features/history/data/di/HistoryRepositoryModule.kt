package com.example.didi.features.history.data.di

import com.example.didi.features.history.data.repositories.HistoryRepositoryImpl
import com.example.didi.features.history.domain.repositories.HistoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HistoryRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHistoryRepository(
        historyRepositoryImpl: HistoryRepositoryImpl
    ): HistoryRepository
}
