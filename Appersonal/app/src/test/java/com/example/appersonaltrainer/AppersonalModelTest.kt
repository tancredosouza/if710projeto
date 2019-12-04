package com.example.appersonaltrainer

import com.example.appersonaltrainer.components.Time
import com.example.appersonaltrainer.components.TimerState
import com.example.appersonaltrainer.model.AppersonalTimerModel
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Test


class AppersonalViewModelTest {
    private lateinit var timerModel: AppersonalTimerModel

    @Before
    fun init() {
        timerModel = AppersonalTimerModel(Time(0,0,10))
    }

    @Test
    fun testStartCounting() {
        timerModel.startTimer()

        assertEquals(TimerState.COUNTING, timerModel.timerState)
    }

    @Test
    fun testStopCounting() {
        timerModel.stopTimer()

        assertEquals(TimerState.STOPPED, timerModel.timerState)
    }

    @Ignore
    fun testGetLiveDataFromTimer() {
        timerModel.startTimer()
        val x = timerModel.getLiveDataFromTimer()

        // TODO: This test isn't working. I don't know how one would test this
        assertEquals(10, x.value)
    }
}
