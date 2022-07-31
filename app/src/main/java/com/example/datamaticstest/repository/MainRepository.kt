package com.example.datamaticstest.repository

import androidx.lifecycle.MutableLiveData
import com.example.datamaticstest.model.CakeResponse
import com.example.datamaticstest.service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {

    fun makeApiCall(liveDataList: MutableLiveData<CakeResponse>) {
        val call: Call<CakeResponse> = apiService.getData()
        call.enqueue(object : Callback<CakeResponse> {
            override fun onFailure(call: Call<CakeResponse>, t: Throwable) {
                liveDataList.postValue(null)
            }

            override fun onResponse(call: Call<CakeResponse>, response: Response<CakeResponse>) {
                liveDataList.postValue(response.body())
            }
        })
    }
}