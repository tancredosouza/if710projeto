package com.example.appersonaltrainer.contract

import androidx.lifecycle.LifecycleOwner

interface AppersonalContract {
    interface View: LifecycleOwner {
        fun updateDisplayedTime(currentTimeInSeconds: Long)

        fun getContext(): LifecycleOwner

        fun getUserInput(): Long
    }

    interface Presenter {
        fun observeDisplayedTimeChanges()

        fun handleButtonPress()
    }
}
