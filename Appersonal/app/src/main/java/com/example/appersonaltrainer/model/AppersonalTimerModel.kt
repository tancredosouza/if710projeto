package com.example.appersonaltrainer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*


class AppersonalTimerModel(private var initialTimeInSeconds: Long) {
    private val TIMER: Timer

    private val TIMER_TASK: TimerTask

    private val elapsedTimeInSeconds: MutableLiveData<Long>

    fun getElapsedTimeInSeconds(): LiveData<Long> {
        return elapsedTimeInSeconds
    }

    init {
        TIMER = Timer()
        TIMER_TASK = createTimerTask()
        elapsedTimeInSeconds = MutableLiveData()

        updateTheElapsedTimeEverySecond()
    }

    private fun createTimerTask(): TimerTask {
        return object : TimerTask() {
            override fun run() {
                elapsedTimeInSeconds.postValue(initialTimeInSeconds)

                initialTimeInSeconds -= 1
            }
        }
    }

    private fun updateTheElapsedTimeEverySecond(): Unit {
        TIMER.scheduleAtFixedRate(TIMER_TASK, ONE_SECOND_IN_MILLISECONDS, ONE_SECOND_IN_MILLISECONDS)
    }

    companion object {
        const val ONE_SECOND_IN_MILLISECONDS: Long = 1000
    }
}
