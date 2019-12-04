package com.example.appersonaltrainer.activities

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleOwner
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.components.Time
import com.example.appersonaltrainer.components.TimerState
import com.example.appersonaltrainer.contract.AppersonalContract
import com.example.appersonaltrainer.databases.SeriesDB
import com.example.appersonaltrainer.model.Series
import com.example.appersonaltrainer.view_model.AppersonalViewModel
import kotlinx.android.synthetic.main.series_happening_activity.current_exercise_remaining_time
import kotlinx.android.synthetic.main.series_happening_activity.current_exercise_type
import kotlinx.android.synthetic.main.series_happening_activity.next_exercise_button
import kotlinx.android.synthetic.main.series_happening_activity.next_exercise_layout
import kotlinx.android.synthetic.main.series_happening_activity.next_exercise_remaining_time
import kotlinx.android.synthetic.main.series_happening_activity.next_exercise_type
import kotlinx.android.synthetic.main.series_happening_activity.play_pause_button
import kotlinx.android.synthetic.main.series_happening_activity.series_name
import kotlinx.android.synthetic.main.series_happening_activity.stop_series_button
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class SeriesHappeningActivity : AppCompatActivity(), AppersonalContract.View {
    private lateinit var viewModel: AppersonalViewModel
    private lateinit var seriesHappening: Series
    private var e: Int = 0

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
                setupViewModelAndActivity(e)
            }
        }
    }

    private fun setupViewModelAndActivity(e: Int) {
        setupViewModel(e)
        setupActivity(e)
    }

    private fun setupViewModel(e: Int) {
        viewModel = AppersonalViewModel(this, seriesHappening, e)
        setupButtonClickListener()
    }

    private fun setupButtonClickListener() {
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

        current_exercise_remaining_time.text = exercises[e].totalTime.toString()
        current_exercise_type.text = exercises[e].type.toString()
        if (exercises.size > 1 && e + 1 < exercises.size) {
            next_exercise_remaining_time.text = exercises[e + 1].totalTime.toString()
            next_exercise_type.text = exercises[e + 1].type.toString()
            next_exercise_layout.visibility = View.VISIBLE
            next_exercise_button.visibility = View.VISIBLE
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

    override fun updateImageResource(timerState: TimerState) {
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
            sendNotification()
            goToNextExercise()
        }
    }

    private fun goToNextExercise() {
        if (e + 1 < seriesHappening.exercises.size) {
            e += 1
            setupViewModelAndActivity(e)
            viewModel.handleButtonPress()
        } else {
            showCompletedDialog()
        }
    }

    private fun buildCompletedDialog(): AlertDialog.Builder? {
        val builder: AlertDialog.Builder? = this.let {
            AlertDialog.Builder(it)
        }

        builder?.apply {
            setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, id ->
                    viewModel.shutdown()
                    e = 0
                    updateImageResource(TimerState.STOPPED)
                    setupViewModelAndActivity(e)
                })
        }

// 2. Chain together various setter methods to set the dialog characteristics
        builder?.setMessage("Você acabou de completar '${seriesHappening.name}'")?.setTitle("Completado!")
        return builder
    }

    private fun showCompletedDialog() {
        val dialog = buildCompletedDialog()
        dialog?.show()
    }

    private fun sendNotification() {
        createNotificationChannel()

        val builder = NotificationCompat.Builder(this, "CHANNELID")
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Appersonal")
            .setContentText("Próximo exercício!")
            .setPriority(NotificationCompat.PRIORITY_MAX)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(8978969, builder.build())
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "CHANNELNAME"
            val descriptionText = "Appersonal Notification Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNELID", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
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
