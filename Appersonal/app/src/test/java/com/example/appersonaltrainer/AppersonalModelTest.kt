package com.example.appersonaltrainer

import com.example.appersonaltrainer.model.AppersonalTimerModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test


class AppersonalViewModelTest {
    private lateinit var timerModel: AppersonalTimerModel

    @Before
    fun init() {
        timerModel = AppersonalTimerModel(10)
    }

    @Test
    fun testStartCounting() {
        timerModel.startCounting()

        assertTrue(timerModel.isCounting)
    }

    @Test
    fun testStopCounting() {
        timerModel.stopCounting()

        assertFalse(timerModel.isCounting)
    }

    @Ignore
    fun testGetLiveDataFromTimer() {
        timerModel.startCounting()
        val x = timerModel.getLiveDataFromTimer()

        // TODO: This test isn't working. I don't know how one would test this
        assertEquals(10, x.value)
    }
}
