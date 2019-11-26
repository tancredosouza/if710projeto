package com.example.appersonaltrainer.components

data class Exercise(
    val type: ExerciseType,
    val totalTime: Time
) {
    override fun toString(): String {
        return type.toString()
    }
}
