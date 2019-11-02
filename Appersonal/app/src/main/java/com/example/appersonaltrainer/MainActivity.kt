package com.example.appersonaltrainer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.example.appersonaltrainer.contract.AppersonalContract
import com.example.appersonaltrainer.view_model.AppersonalViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), AppersonalContract.View {
    private lateinit var viewModel: AppersonalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setupButtonClickListener()
    }

    private fun setupViewModel() {
        viewModel = AppersonalViewModel(this)
    }

    private fun setupButtonClickListener() {
        action_button.apply {
            setOnClickListener {
                setImageResource(android.R.drawable.ic_media_pause)
                viewModel.handleButtonPress()
            }
        }
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
