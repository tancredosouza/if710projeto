package com.example.appersonaltrainer

import android.os.CountDownTimer
import android.widget.TextView

class ExerciseTimer(textView: TextView, exerciseDuration: Long) {
    private var timer: CountDownTimer? = null

    init {
        timer = initTimer(textView, exerciseDuration)
    }

    fun start() {
        timer!!.start()
    }

    private fun initTimer(textView: TextView, exerciseDuration: Long): CountDownTimer {
        return object : CountDownTimer(exerciseDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textView.text = "seconds remaining: " + millisUntilFinished / 1000
            }

            override fun onFinish() {
                textView.text = "Time's finished!"
            }
        }
    }
}
