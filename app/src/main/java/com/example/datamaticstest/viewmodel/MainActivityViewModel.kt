package com.example.datamaticstest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.datamaticstest.model.CakeResponse
import com.example.datamaticstest.repository.MainRepository
import com.example.datamaticstest.room.Donut
import com.example.datamaticstest.room.DonutRepository
import com.example.datamaticstest.room.DonutRepositoryImpl
import com.example.datamaticstest.service.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: MainRepository,
    private val roomRepository: DonutRepositoryImpl
) :
    ViewModel() {

    private var liveDataList: MutableLiveData<CakeResponse> = MutableLiveData()

    fun getLiveData(): MutableLiveData<CakeResponse> {
        return liveDataList
    }

    fun loadData() {
        repository.makeApiCall(liveDataList)
    }

    suspend fun insertDonut(donut: Donut) = roomRepository.insertDonut(donut)

    fun getDonuts(id: Int) = roomRepository.getSpecificDonutDetails(id)

    fun getAllDonuts() = roomRepository.getAllDonuts()

}