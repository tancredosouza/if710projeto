package com.example.appersonaltrainer.components

import kotlin.random.Random

data class Exercise(
    val name: String,
    val totalDuration: Long,

    val id: String = Random.nextInt().toString()
) {
    override fun toString(): String {
        return name
    }
}
