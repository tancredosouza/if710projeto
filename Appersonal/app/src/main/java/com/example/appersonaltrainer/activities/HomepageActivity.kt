package com.example.appersonaltrainer.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.adapters.SeriesAdapter
import com.example.appersonaltrainer.components.Series
import com.example.appersonaltrainer.databases.SeriesDB
import kotlinx.android.synthetic.main.homepage_activity.create_series_button
import kotlinx.android.synthetic.main.homepage_activity.series_list
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HomepageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage_activity)

        setupButtons()
        loadSeriesOfUser()
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

    fun loadSeriesOfUser() {
        val db = SeriesDB.getDatabase(this)

        doAsync {
            val items = db.getAccessObject().getAllCreatedSeries()

            uiThread {
                updateSeriesList(items.toList())
            }
        }
    }

    fun updateSeriesList(seriesList: List<Series>) {
        series_list.apply {
            layoutManager = LinearLayoutManager(this@HomepageActivity)
            adapter = SeriesAdapter(seriesList, this@HomepageActivity)
        }
    }

    override fun onResume() {
        super.onResume()

        loadSeriesOfUser()
    }

}
