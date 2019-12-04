package com.example.appersonaltrainer.view_model

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.appersonaltrainer.components.TimerState
import com.example.appersonaltrainer.contract.AppersonalContract
import com.example.appersonaltrainer.model.AppersonalTimerModel
import com.example.appersonaltrainer.model.Series

class AppersonalViewModel(private val viewToPresent: AppersonalContract.View, series: Series) :
    ViewModel() {
    private val timerModel: AppersonalTimerModel

    private lateinit var elapsedTimeObserver: Observer<Long>

    fun handleButtonPress() {
        if (timerModel.timerState == TimerState.STOPPED) {
            observeDisplayedTimeChanges()
            timerModel.startCounting()
        } else {
            shutdown()
        }
        viewToPresent.updateImageResource(timerModel.timerState)
    }

    fun shutdown() {
        if (timerModel.timerState == TimerState.COUNTING) {
            timerModel.stopCounting()
        }

        timerModel.getLiveDataFromTimer()
            .removeObserver(elapsedTimeObserver)
    }

    init {
        timerModel = AppersonalTimerModel(series.exercises[0].totalTime)

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
