package com.example.appersonaltrainer.model

import androidx.lifecycle.LiveData
import com.example.appersonaltrainer.components.ExerciseTimer

class AppersonalTimerModel(initialTimeInSeconds: Long) {
    private val EXERCISE_TIMER: ExerciseTimer

    init {
        EXERCISE_TIMER = ExerciseTimer(initialTimeInSeconds)
    }

    fun startCounting() {
        EXERCISE_TIMER.startCounting()
    }

    fun getElapsedTimeInSeconds(): LiveData<Long> {
        return EXERCISE_TIMER.getElapsedTimeInSeconds()
    }
}
