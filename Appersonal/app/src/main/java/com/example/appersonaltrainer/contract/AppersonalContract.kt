package com.example.appersonaltrainer.contract

import androidx.lifecycle.LifecycleOwner
import com.example.appersonaltrainer.components.TimerState

interface AppersonalContract {
    interface View : LifecycleOwner {
        fun updateDisplayedTime(currentTimeInSeconds: Long)

        fun getContext(): LifecycleOwner

        fun updateImageResource(timerState : TimerState)
    }
}
