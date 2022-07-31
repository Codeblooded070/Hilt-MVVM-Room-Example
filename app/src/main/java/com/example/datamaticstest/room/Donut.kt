package com.example.datamaticstest.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "donut_table")
data class Donut(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val donutName: String,
    val toppingCount: Int,
    val toppingName: String
)