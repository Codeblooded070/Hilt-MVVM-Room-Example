package com.example.datamaticstest.room

import androidx.lifecycle.LiveData

interface DonutRepository {
    fun getAllDonuts(): LiveData<List<Donut>>
    fun getSpecificDonutDetails(id: Int): LiveData<List<Donut>>
    suspend fun insertDonut(note: Donut)
}