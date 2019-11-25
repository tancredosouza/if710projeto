package com.example.appersonaltrainer.components

data class Exercise(
    val name: ExerciseType,
    val totalTime: Time
) {
    override fun toString(): String {
        return name.toString()
    }
}
