package com.example.datamaticstest.di

import com.example.datamaticstest.room.DonutDao
import com.example.datamaticstest.room.DonutRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesDonutRepository(donutDao: DonutDao) = DonutRepositoryImpl(donutDao)

}