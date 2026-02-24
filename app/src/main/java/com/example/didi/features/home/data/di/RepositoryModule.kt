package com.example.didi.features.home.data.di


import com.example.didi.features.home.data.repositories.RidesRepositoryImpl
import com.example.didi.features.home.domain.repositories.RidesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRidesRepository(
        ridesRepositoryImpl: RidesRepositoryImpl
    ): RidesRepository
}