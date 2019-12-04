package com.example.appersonaltrainer.view_model

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.appersonaltrainer.components.Time
import com.example.appersonaltrainer.components.TimerState
import com.example.appersonaltrainer.contract.AppersonalContract
import com.example.appersonaltrainer.model.AppersonalTimerModel
import com.example.appersonaltrainer.model.Series

class AppersonalViewModel(private val viewToPresent: AppersonalContract.View, series: Series) :
    ViewModel() {
    private var timerModel: AppersonalTimerModel

    private lateinit var elapsedTimeObserver: Observer<Long>

    fun handleButtonPress() {
        if (timerModel.timerState == TimerState.STOPPED) {
            observeDisplayedTimeChanges()
            timerModel.startTimer()
        } else if (timerModel.timerState == TimerState.COUNTING) {
            timerModel.pauseTimer()
            timerModel = AppersonalTimerModel(getTimeFromSeconds(timerModel.getLiveDataFromTimer().value!!))
        } else {
            observeDisplayedTimeChanges()
            timerModel.startTimer()
        }
        viewToPresent.updateImageResource(timerModel.timerState)
    }



    fun shutdown() {
        if (timerModel.timerState == TimerState.COUNTING) {
            timerModel.stopTimer()
        }

        timerModel.getLiveDataFromTimer()
            .removeObserver(elapsedTimeObserver)
    }

    init {
        timerModel = AppersonalTimerModel(series.exercises[0].totalTime)

        observeDisplayedTimeChanges()
    }

    fun getTimeFromSeconds(seconds: Long): Time {
        val numberOfHours = seconds / 3600
        val numberOfMinutes = (seconds % 3600) / 60
        val numberOfSeconds = ((seconds % 3600) % 60)

        return Time(numberOfHours, numberOfMinutes, numberOfSeconds)
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
