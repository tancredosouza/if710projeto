package com.example.appersonaltrainer.components

data class Exercise(
    val name: String,
    val hours: Long,
    val minutes: Long,
    val seconds: Long
) {
    override fun toString(): String {
        return name
    }
}
