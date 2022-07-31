package com.example.datamaticstest.room

import androidx.lifecycle.LiveData
import javax.inject.Inject

class DonutRepositoryImpl @Inject constructor(private val donutDao: DonutDao) : DonutRepository {

    override fun getAllDonuts(): LiveData<List<Donut>> {
        return donutDao.getAllDonuts()
    }

    override suspend fun insertDonut(donutValue: Donut) {
        return donutDao.insertDonut(donutValue)
    }

    override fun getSpecificDonutDetails(id: Int): LiveData<List<Donut>> {
        return donutDao.getSpecificDonutDetails(id)
    }

}