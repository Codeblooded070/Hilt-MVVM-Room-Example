package com.example.datamaticstest.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface DonutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //if some data is same/conflict, it'll be replace with new data.
    suspend fun insertDonut(donut: Donut)

    @Query("SELECT * FROM donut_table")
    fun getAllDonuts(): LiveData<List<Donut>>

    @Query("SELECT * FROM donut_table where id = :id")
    fun getSpecificDonutDetails(id: Int): LiveData<List<Donut>>

}