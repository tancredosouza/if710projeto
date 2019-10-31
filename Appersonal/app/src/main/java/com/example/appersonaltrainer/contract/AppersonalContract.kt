package com.example.appersonaltrainer.contract

import androidx.lifecycle.LifecycleOwner

interface AppersonalContract {
    interface View {
        fun updateDisplayedTime(currentTimeInSeconds: String)

        fun getContext(): LifecycleOwner

        fun getUserInput(): Long
    }

    interface Presenter {
        fun setup()

        fun handleButtonPress()
    }
}
