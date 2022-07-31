package com.example.datamaticstest.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Donut::class], version = 1, exportSchema = false)
abstract class DonutDatabase : RoomDatabase() {
    abstract fun getDonutDao(): DonutDao

    companion object {
        const val DB_NAME = "donut_database.db"
    }

}