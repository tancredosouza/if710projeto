package com.example.appersonaltrainer.components

data class Exercise(
    val name: String,
    val totalTime: Time
) {
    override fun toString(): String {
        return name
    }
}
