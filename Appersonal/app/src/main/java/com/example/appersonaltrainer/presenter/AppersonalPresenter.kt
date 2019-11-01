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

        observeDisplayedTimeChanges()
    }

    override fun observeDisplayedTimeChanges() {
        val elapsedTimeObserver = Observer<Long> { newDisplayedTime ->
            viewToPresent.updateDisplayedTime(newDisplayedTime)
        }

        timerModel.getLiveDataFromTimer()
            .observe(viewToPresent.getContext(), elapsedTimeObserver)
    }

    override fun handleButtonPress() {
        timerModel.startCounting()
    }
}
