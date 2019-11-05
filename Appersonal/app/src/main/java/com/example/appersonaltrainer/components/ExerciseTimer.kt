package com.example.appersonaltrainer.components

import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.util.Timer
import java.util.TimerTask

class ExerciseTimer private constructor(private var initialTimeInSeconds: Long) {
    private lateinit var TIMER_TASK: TimerTask

    private val timerLiveData: MutableLiveData<Long>

    fun getTimerLiveData(): MutableLiveData<Long> {
        return timerLiveData
    }

    fun startCounting() {
        setupShouldUpdateTimeEverySecond()
    }

    fun stopCounting() {
        TIMER_TASK.cancel()
    }

    init {
        timerLiveData = MutableLiveData()
        TIMER_TASK = createTimerTask()
    }

    private fun createTimerTask(): TimerTask {
        return object : TimerTask() {
            override fun run() {
                Log.v("Appersonal", "Update displayed time to $initialTimeInSeconds")
                timerLiveData.postValue(initialTimeInSeconds)

                initialTimeInSeconds -= 1
            }
        }
    }

    private fun setupShouldUpdateTimeEverySecond() {
        val timer = Timer()
        val oneSecondInMilliseconds: Long = 1000

        timer.scheduleAtFixedRate(
            TIMER_TASK,
            oneSecondInMilliseconds,
            oneSecondInMilliseconds
        )
    }

    companion object {
        fun withInitialTime(timeInSeconds: Long) =
            ExerciseTimer(timeInSeconds)
    }
}
