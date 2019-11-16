package com.example.appersonaltrainer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.contract.AppersonalContract
import com.example.appersonaltrainer.view_model.AppersonalViewModel
import kotlinx.android.synthetic.main.timer_activity.action_button
import kotlinx.android.synthetic.main.timer_activity.timerField

class TimerActivity : AppCompatActivity(), AppersonalContract.View {
    private lateinit var viewModel: AppersonalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timer_activity)

        setupViewModel()
        setupButtonClickListener()
    }

    private fun setupViewModel() {
        viewModel = AppersonalViewModel(this)
    }

    private fun setupButtonClickListener() {
        action_button.apply {
            setOnClickListener {
                viewModel.handleButtonPress()
            }
        }
    }

    override fun buttonDisplaysThatCountingStarted() {
        action_button.setImageResource(android.R.drawable.ic_media_pause)
    }

    override fun buttonDisplaysThatCountingStopped() {
        action_button.setImageResource(android.R.drawable.ic_media_play)
    }

    override fun onDestroy() {
        viewModel.shutdown()
        super.onDestroy()
    }

    override fun updateDisplayedTime(currentTimeInSeconds: Long) {
        timerField.text = currentTimeInSeconds.toString()
    }

    override fun getContext(): LifecycleOwner {
        return this
    }

    override fun getUserInput(): Long {
        return timerField.text.toString().toLong()
    }
}
