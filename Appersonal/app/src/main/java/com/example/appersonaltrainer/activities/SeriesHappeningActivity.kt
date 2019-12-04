package com.example.appersonaltrainer.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.components.ExerciseTimer.Companion.CURRENT_EXERCISE_ENDED
import com.example.appersonaltrainer.components.Time
import com.example.appersonaltrainer.components.TimerState
import com.example.appersonaltrainer.contract.AppersonalContract
import com.example.appersonaltrainer.databases.SeriesDB
import com.example.appersonaltrainer.model.Series
import com.example.appersonaltrainer.view_model.AppersonalViewModel
import kotlinx.android.synthetic.main.series_happening_activity.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SeriesHappeningActivity : AppCompatActivity(), AppersonalContract.View {
    private lateinit var viewModel: AppersonalViewModel
    private lateinit var seriesHappening: Series
    private var e: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.series_happening_activity)

        Log.d("SeriesHappeningActivity", "onCreate")
        val seriesId: String = intent.getStringExtra("series_id")!!
        loadSeriesFromDatabase(seriesId)
    }

    private fun loadSeriesFromDatabase(seriesId: String) {
        val db = SeriesDB.getDatabase(applicationContext)

        Log.d("SeriesHappeningActivity", "loaded")
        doAsync {
            seriesHappening = db.getAccessObject().getSeriesWithId(seriesId)

            uiThread {
                setupViewModelAndActivity(e)
            }
        }
    }

    private fun setupViewModelAndActivity(e: Int) {
        setupViewModel(e)
        setupActivity(e)
    }

    private fun setupViewModel(e: Int) {
        Log.d("SeriesHappeningActivity", "viewmodel")
        viewModel = AppersonalViewModel(this, seriesHappening, e)
        setupButtonClickListener()
    }

    private fun setupButtonClickListener() {
        Log.d("SeriesHappeningActivity", "setupButtonClickListener")
        play_pause_button.apply {
            setOnClickListener {
                viewModel.handleButtonPress()
            }
        }
        stop_series_button.apply {
            setOnClickListener {
                val intent = Intent(this@SeriesHappeningActivity, HomepageActivity::class.java)
                startActivity(intent)
                viewModel.shutdown()
                finish()
            }
        }
        next_exercise_button.apply {
            setOnClickListener {
                goToNextExercise()
            }
        }
    }

    private fun setupActivity(e: Int) {
        val exercises = seriesHappening.exercises

        Log.d("SeriesHappeningActivity", "setupactivity")
        current_exercise_remaining_time.text = exercises[e].totalTime.toString()
        current_exercise_type.text = exercises[e].type.toString()
        if (exercises.size > 1 && e + 1 < exercises.size) {
            next_exercise_remaining_time.text = exercises[e + 1].totalTime.toString()
            next_exercise_type.text = exercises[e + 1].type.toString()
        } else {
            next_exercise_layout.visibility = View.INVISIBLE
            next_exercise_button.visibility = View.INVISIBLE
        }
        series_name.text = seriesHappening.name
    }

    override fun onDestroy() {
        viewModel.shutdown()
        super.onDestroy()
    }

    override fun updateImageResource(timerState : TimerState) {
        if (timerState == TimerState.COUNTING) {
            play_pause_button.setImageResource(android.R.drawable.ic_media_pause)
        } else {
            play_pause_button.setImageResource(android.R.drawable.ic_media_play)
        }
    }

    override fun updateDisplayedTime(currentTimeInSeconds: Long) {
        current_exercise_remaining_time.text =
            getTimeFromSeconds(currentTimeInSeconds).toString()

        if (currentTimeInSeconds == 0L) {
            goToNextExercise()
        }
    }

    private fun goToNextExercise() {
        if (e + 1 < seriesHappening.exercises.size) {
            // TODO: send notification that exercise ended
            e += 1
            setupViewModelAndActivity(e)
            viewModel.handleButtonPress()
        }
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
