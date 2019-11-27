package com.example.appersonaltrainer.model

import com.example.appersonaltrainer.components.ExerciseType
import com.example.appersonaltrainer.components.Time

data class Exercise(
    val type: ExerciseType,
    val totalTime: Time
) {
    override fun toString(): String {
        return type.toString()
    }
}
