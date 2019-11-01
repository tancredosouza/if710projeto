package com.example.appersonaltrainer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.example.appersonaltrainer.contract.AppersonalContract
import com.example.appersonaltrainer.presenter.AppersonalPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), AppersonalContract.View {
    private var presenter: AppersonalContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPresenter()
        setupButton()
    }

    private fun setupPresenter() {
        presenter = AppersonalPresenter(this)
    }

    private fun setupButton() {
        action_button.apply {
            setOnClickListener {
                setImageResource(android.R.drawable.ic_media_pause)
                presenter!!.handleButtonPress()
            }
        }
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
