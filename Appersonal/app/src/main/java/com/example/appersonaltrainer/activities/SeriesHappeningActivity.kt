package com.example.appersonaltrainer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.databases.SeriesDB
import com.example.appersonaltrainer.model.Series
import kotlinx.android.synthetic.main.series_happening_activity.current_exercise_remaining_time
import kotlinx.android.synthetic.main.series_happening_activity.current_exercise_type
import kotlinx.android.synthetic.main.series_happening_activity.next_exercise_remaining_time
import kotlinx.android.synthetic.main.series_happening_activity.next_exercise_type
import kotlinx.android.synthetic.main.series_happening_activity.series_name
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SeriesHappeningActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.series_happening_activity)

        val seriesId = intent.getStringExtra("series_id")

        loadSeriesFromDatabase(seriesId)
    }

    private fun loadSeriesFromDatabase(seriesId: String) {
        val db = SeriesDB.getDatabase(applicationContext)

        doAsync {
            val series = db.getAccessObject().getSeriesWithId(seriesId)

            uiThread {
                updateActivity(series)
            }
        }
    }

    private fun updateActivity(series: Series) {
        val exercises = series.exercises

        current_exercise_remaining_time.text = exercises[0].totalTime.toString()
        current_exercise_type.text = exercises[0].type.toString()

        next_exercise_remaining_time.text = exercises[1].totalTime.toString()
        next_exercise_type.text = exercises[1].type.toString()

        series_name.text = series.name
    }
}
