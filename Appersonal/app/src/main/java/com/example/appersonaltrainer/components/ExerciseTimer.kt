package com.example.appersonaltrainer.components

import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.util.*

class ExerciseTimer private constructor(private var initialTimeInSeconds: Long) {
    private lateinit var TIMER_TASK: TimerTask

    private val timerLiveData: MutableLiveData<Long>

    fun getTimerLiveData(): MutableLiveData<Long> {
        return timerLiveData
    }

    fun startCounting() {
        TIMER_TASK = createTimerTask()
        setupShouldUpdateTimeEverySecond()
    }

    fun stopCounting() {
        assert(TIMER_TASK.cancel())
    }

    init {
        timerLiveData = MutableLiveData()
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
