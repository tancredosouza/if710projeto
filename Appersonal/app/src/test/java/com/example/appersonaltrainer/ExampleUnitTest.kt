package com.example.appersonaltrainer

import com.example.appersonaltrainer.contract.AppersonalContract
import com.example.appersonaltrainer.model.AppersonalTimerModel
import com.example.appersonaltrainer.presenter.AppersonalPresenter
import org.junit.Test
import org.mockito.Mockito

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun whenUserClicksActionButton_shouldStartCountingTime() {
        val fakeCountdownTimer: AppersonalTimerModel = Mockito.mock(
            AppersonalTimerModel::class.java)

        val fakeView: AppersonalContract.View = Mockito.mock(AppersonalContract.View::class.java)
        Mockito.doNothing().`when`(fakeView).updateDisplayedTime(Mockito.anyString())

        val presenter = AppersonalPresenter(fakeView)
    }
}
