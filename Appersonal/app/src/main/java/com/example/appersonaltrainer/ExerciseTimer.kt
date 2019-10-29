package com.example.appersonaltrainer

import android.os.CountDownTimer
import android.widget.TextView

class ExerciseTimer(view: TextView, exerciseTotalTime: Long) {
    private val timer: CountDownTimer
    private val textView: TextView
    private val exerciseCurrentTime: Long

    init {
        textView = view
        exerciseCurrentTime = exerciseTotalTime
        timer = initTimer()
    }

    fun start() {
        timer.start()
    }

    private fun initTimer(): CountDownTimer {
        return object : CountDownTimer(exerciseCurrentTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textView.text = "seconds remaining: " + millisUntilFinished / 1000
            }

            override fun onFinish() {
                textView.text = "Time's finished!"
            }
        }
    }
}
