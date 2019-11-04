package com.example.appersonaltrainer.view_model

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.appersonaltrainer.contract.AppersonalContract
import com.example.appersonaltrainer.model.AppersonalTimerModel

class AppersonalViewModel(private val viewToPresent: AppersonalContract.View) : ViewModel() {
    private val timerModel: AppersonalTimerModel

    private lateinit var elapsedTimeObserver: Observer<Long>

    fun handleButtonPress() {
        if (!timerModel.isCounting) {
            observeDisplayedTimeChanges()
            viewToPresent.buttonDisplaysThatCountingStarted()
            timerModel.startCounting()
        } else {
            shutdown()
        }
    }

    fun shutdown() {
        if (timerModel.isCounting) {
            timerModel.stopCounting()
        }

        viewToPresent.buttonDisplaysThatCountingStopped()

        timerModel.getLiveDataFromTimer()
            .removeObserver(elapsedTimeObserver)
    }

    init {
        timerModel =
            AppersonalTimerModel(viewToPresent.getUserInput())

        observeDisplayedTimeChanges()
    }

    private fun observeDisplayedTimeChanges() {
        elapsedTimeObserver = Observer { newDisplayedTime ->
            viewToPresent.updateDisplayedTime(newDisplayedTime)
        }

        timerModel
            .getLiveDataFromTimer()
            .observe(viewToPresent.getContext(), elapsedTimeObserver)
    }
}
