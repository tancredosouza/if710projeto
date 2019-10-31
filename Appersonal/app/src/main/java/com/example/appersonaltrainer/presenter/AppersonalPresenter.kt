package com.example.appersonaltrainer.presenter

import androidx.lifecycle.Observer
import com.example.appersonaltrainer.contract.AppersonalContract
import com.example.appersonaltrainer.model.AppersonalTimerModel

class AppersonalPresenter(private val viewToPresent: AppersonalContract.View) :
    AppersonalContract.Presenter {
    private val timerModel: AppersonalTimerModel

    init {
        timerModel =
            AppersonalTimerModel(viewToPresent.getUserInput())
    }

    override fun setup() {
        val elapsedTimeObserver = Observer<Long> { aLong ->
            val newText = aLong.toString()
            viewToPresent.updateDisplayedTime(newText)
        }

        timerModel.getElapsedTimeInSeconds()
            .observe(viewToPresent.getContext(), elapsedTimeObserver)
    }

    override fun handleButtonPress() {
        timerModel.startCounting()
    }
}
