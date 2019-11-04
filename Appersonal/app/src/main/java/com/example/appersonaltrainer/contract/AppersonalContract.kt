package com.example.appersonaltrainer.contract

import androidx.lifecycle.LifecycleOwner
import kotlinx.android.synthetic.main.activity_main.*

interface AppersonalContract {
    interface View : LifecycleOwner {
        fun updateDisplayedTime(currentTimeInSeconds: Long)

        fun getContext(): LifecycleOwner

        fun getUserInput(): Long

        fun buttonDisplaysThatCountingStarted()

        fun buttonDisplaysThatCountingStopped()
    }
}
