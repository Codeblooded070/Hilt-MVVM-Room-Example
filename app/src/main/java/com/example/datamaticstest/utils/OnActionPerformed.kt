package com.example.datamaticstest.utils

import com.example.datamaticstest.model.CakeResponse

interface OnActionPerformed {

    fun onSelect(id: Int, name: String, topping: List<CakeResponse.CakeResponseItem.Topping>)

    fun onSelectOffline(id: Int)

}