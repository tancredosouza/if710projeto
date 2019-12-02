package com.example.appersonaltrainer.components

import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.util.Timer
import java.util.TimerTask

class ExerciseTimer private constructor(private var remainingTime: Long) {
    private val TIMER_TASK: TimerTask
    private val TIMER_LIVE_DATA: MutableLiveData<Long>

    fun getTimerLiveData(): MutableLiveData<Long> {
        return TIMER_LIVE_DATA
    }

    fun startCounting() {
        setupShouldUpdateTimeEverySecond()
    }

    fun stopCounting() {
        TIMER_TASK.cancel()
    }

    init {
        TIMER_LIVE_DATA = MutableLiveData()
        TIMER_TASK = createTimerTask()
    }

    private fun createTimerTask(): TimerTask {
        return object : TimerTask() {
            override fun run() {
                Log.v("Appersonal", "Update displayed time to $remainingTime")
                TIMER_LIVE_DATA.postValue(remainingTime)

                remainingTime -= 1

                if (remainingTime <= 0) {
                    finishTimer()
                }
            }
        }
    }

    private fun finishTimer() {
        // TODO: should change to the next exercise (use a broadcast)
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
