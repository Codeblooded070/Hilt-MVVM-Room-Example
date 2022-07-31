package com.example.datamaticstest.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.datamaticstest.room.Donut
import com.example.datamaticstest.room.DonutDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.modules.ApplicationContextModule
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): DonutDatabase {
        return Room.databaseBuilder(context, DonutDatabase::class.java, DonutDatabase.DB_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun providesNoteDao(donutDatabase: DonutDatabase) = donutDatabase.getDonutDao()
}