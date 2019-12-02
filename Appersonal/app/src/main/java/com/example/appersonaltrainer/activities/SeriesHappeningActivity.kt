package com.example.appersonaltrainer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.components.Time
import com.example.appersonaltrainer.contract.AppersonalContract
import com.example.appersonaltrainer.databases.SeriesDB
import com.example.appersonaltrainer.model.Series
import com.example.appersonaltrainer.view_model.AppersonalViewModel
import kotlinx.android.synthetic.main.series_happening_activity.current_exercise_remaining_time
import kotlinx.android.synthetic.main.series_happening_activity.current_exercise_type
import kotlinx.android.synthetic.main.series_happening_activity.next_exercise_remaining_time
import kotlinx.android.synthetic.main.series_happening_activity.next_exercise_type
import kotlinx.android.synthetic.main.series_happening_activity.play_pause_button
import kotlinx.android.synthetic.main.series_happening_activity.series_name
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SeriesHappeningActivity : AppCompatActivity(), AppersonalContract.View {
    private lateinit var viewModel: AppersonalViewModel
    private lateinit var seriesHappening: Series

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.series_happening_activity)

        val seriesId: String = intent.getStringExtra("series_id")!!
        loadSeriesFromDatabase(seriesId)
    }

    private fun loadSeriesFromDatabase(seriesId: String) {
        val db = SeriesDB.getDatabase(applicationContext)

        doAsync {
            seriesHappening = db.getAccessObject().getSeriesWithId(seriesId)

            uiThread {
                setupViewModel()
                setupActivity()
            }
        }
    }

    private fun setupViewModel() {
        viewModel = AppersonalViewModel(this, seriesHappening)
        setupButtonClickListener()
    }

    private fun setupButtonClickListener() {
        play_pause_button.apply {
            setOnClickListener {
                viewModel.handleButtonPress()
            }
        }
    }

    private fun setupActivity() {
        val exercises = seriesHappening.exercises

        current_exercise_remaining_time.text = exercises[0].totalTime.toString()
        current_exercise_type.text = exercises[0].type.toString()

        next_exercise_remaining_time.text = exercises[1].totalTime.toString()
        next_exercise_type.text = exercises[1].type.toString()

        series_name.text = seriesHappening.name
    }

    override fun onDestroy() {
        viewModel.shutdown()
        super.onDestroy()
    }

    override fun updateDisplayedTime(currentTimeInSeconds: Long) {
        current_exercise_remaining_time.text =
            getTimeFromSeconds(currentTimeInSeconds).toString()
    }

    fun getTimeFromSeconds(seconds: Long): Time {
        val numberOfHours = seconds / 3600
        val numberOfMinutes = (seconds % 3600) / 60
        val numberOfSeconds = ((seconds % 3600) % 60)

        return Time(numberOfHours, numberOfMinutes, numberOfSeconds)
    }

    override fun getContext(): LifecycleOwner {
        return this
    }
}
