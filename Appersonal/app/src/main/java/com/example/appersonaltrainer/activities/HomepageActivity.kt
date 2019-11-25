package com.example.appersonaltrainer.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.view_model.HomepageViewModel
import kotlinx.android.synthetic.main.homepage_activity.create_series_button

class HomepageActivity : AppCompatActivity() {
    lateinit var homepageViewModel: HomepageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.homepage_activity)

        homepageViewModel = HomepageViewModel(applicationContext, this)

        homepageViewModel.loadSeriesOfUser()

        setupButtons()
    }

    private fun setupButtons() {
        setupButtonToCreateSeries()
    }

    private fun setupButtonToCreateSeries() {
        create_series_button.setOnClickListener {
            changeToCreateSeriesActivity()
        }
    }

    private fun changeToCreateSeriesActivity() {
        val intent = Intent(this, CreateSeriesActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        homepageViewModel.loadSeriesOfUser()
    }
}
