package com.example.appersonaltrainer.components

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData

class ExerciseTimer private constructor(private var remainingTime: Long) {
    private val COUNTDOWN_TIMER: CountDownTimer
    private val TIMER_LIVE_DATA: MutableLiveData<Long>

    fun getTimerLiveData(): MutableLiveData<Long> {
        return TIMER_LIVE_DATA
    }

    fun startCounting() {
        COUNTDOWN_TIMER.start()
    }

    fun stopCounting() {
        COUNTDOWN_TIMER.cancel()
    }

    init {
        TIMER_LIVE_DATA = MutableLiveData()
        COUNTDOWN_TIMER = createCountdownTimer()
    }

    private fun createCountdownTimer(): CountDownTimer {
        return object : CountDownTimer(remainingTime * 1000, 1000) {
            override fun onFinish() {}

            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished / 1000
                TIMER_LIVE_DATA.postValue(remainingTime)
            }
        }
    }

    companion object {
        fun withInitialTime(timeInSeconds: Long) =
            ExerciseTimer(timeInSeconds)


        val CURRENT_EXERCISE_ENDED =
            "com.example.appersonaltrainer.components.notification.CURRENT_EXERCISE_ENDED"
    }
}
