package com.example.datamaticstest.service

import com.example.datamaticstest.model.CakeResponse
import retrofit2.Call
import retrofit2.http.GET


interface ApiService {

    @GET("0ba63b71-bb15-434e-9da3-98435dcb346d")
    fun getData(): Call<CakeResponse>
}