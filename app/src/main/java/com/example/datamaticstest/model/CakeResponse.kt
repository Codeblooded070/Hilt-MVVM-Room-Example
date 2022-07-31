package com.example.datamaticstest.model

import java.io.Serializable
import java.util.*

class CakeResponse : ArrayList<CakeResponse.CakeResponseItem>(){
    data class CakeResponseItem(
        var id: Int,
        var name: String,
        var topping: List<Topping>,
        var type: String
    ) {
        data class Topping(
            var id: Int,
            var type: String
        )
    }
}