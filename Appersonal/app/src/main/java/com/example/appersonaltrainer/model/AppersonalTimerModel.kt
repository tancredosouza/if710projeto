package com.example.appersonaltrainer.model

import androidx.lifecycle.MutableLiveData
import com.example.appersonaltrainer.components.ExerciseTimer
import com.example.appersonaltrainer.components.Time
import com.example.appersonaltrainer.components.TimerState

class AppersonalTimerModel(initialTime: Time) {
    private val EXERCISE_TIMER: ExerciseTimer

    var timerState = TimerState.STOPPED

    init {
        EXERCISE_TIMER = ExerciseTimer.withInitialTime(initialTime.toSeconds())
    }

    fun startTimer() {
        EXERCISE_TIMER.startCounting()
        timerState = TimerState.COUNTING
    }

    fun pauseTimer() {
        EXERCISE_TIMER.stopCounting()
        timerState = TimerState.PAUSED
    }

    fun stopTimer() {
        EXERCISE_TIMER.stopCounting()
        timerState = TimerState.STOPPED
    }

    fun getLiveDataFromTimer(): MutableLiveData<Long> {
        return EXERCISE_TIMER.getTimerLiveData()
    }
}
