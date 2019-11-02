package com.example.appersonaltrainer.model

import androidx.lifecycle.MutableLiveData
import com.example.appersonaltrainer.components.ExerciseTimer

class AppersonalTimerModel(initialTimeInSeconds: Long) {
    private val EXERCISE_TIMER: ExerciseTimer

    var isCounting: Boolean = false

    init {
        EXERCISE_TIMER = ExerciseTimer.withInitialTime(initialTimeInSeconds)
    }

    fun startCounting() {
        EXERCISE_TIMER.startCounting()
        isCounting = true
    }

    fun stopCounting() {
        EXERCISE_TIMER.stopCounting()
        isCounting = false
    }

    fun getLiveDataFromTimer(): MutableLiveData<Long> {
        return EXERCISE_TIMER.getTimerLiveData()
    }
}
