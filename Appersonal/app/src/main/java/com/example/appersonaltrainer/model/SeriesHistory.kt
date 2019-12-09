package com.example.appersonaltrainer.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity(tableName = "seriesHistory")
data class SeriesHistory (
    val date: String,
    val name: String,

    @PrimaryKey
    val id : String = Random.nextInt().toString()
)
