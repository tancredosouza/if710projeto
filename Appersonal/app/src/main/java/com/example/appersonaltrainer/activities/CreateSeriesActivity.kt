package com.example.appersonaltrainer.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appersonaltrainer.MinMaxFilter
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.adapters.ExerciseAdapter
import com.example.appersonaltrainer.components.Exercise
import com.example.appersonaltrainer.components.Series
import com.example.appersonaltrainer.components.Time
import com.example.appersonaltrainer.databases.SeriesDB
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.create_series_activity.add_new_exercise_button
import kotlinx.android.synthetic.main.create_series_activity.hours_new_exercise
import kotlinx.android.synthetic.main.create_series_activity.list_of_exercises
import kotlinx.android.synthetic.main.create_series_activity.minutes_new_exercise
import kotlinx.android.synthetic.main.create_series_activity.new_exercise_name
import kotlinx.android.synthetic.main.create_series_activity.new_series_name
import kotlinx.android.synthetic.main.create_series_activity.save_new_series_button
import kotlinx.android.synthetic.main.create_series_activity.seconds_new_exercise
import org.jetbrains.anko.doAsync

class CreateSeriesActivity : AppCompatActivity() {
    private val seriesBeingCreated: Series = Series()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_series_activity)

        val minutes = findViewById<TextInputLayout>(R.id.minutes_new_exercise)
        val seconds = findViewById<TextInputLayout>(R.id.minutes_new_exercise)

        minutes.editText!!.filters = arrayOf(MinMaxFilter)
        seconds.editText!!.filters = arrayOf(MinMaxFilter)

        setupButtons()
    }

    private fun setupButtons() {
        setupAddNewExerciseButton()
        setupSaveSeriesButton()
    }

    private fun setupAddNewExerciseButton() {
        add_new_exercise_button.setOnClickListener {
            createExerciseAndAddToSeries()

            seriesBeingCreated.name = new_series_name.editText!!.text.toString()
        }
    }

    private fun createExerciseAndAddToSeries() {
        try {
            val exercise = createExerciseFromUserInput()
            addExerciseToSeries(exercise)

        } catch(e : NumberFormatException) {
            Toast.makeText(this, "Campo de tempo não pode estar vazio!", Toast.LENGTH_LONG).show()
        }
    }

    private fun createExerciseFromUserInput(): Exercise {
        val exerciseName: String = getExerciseNameFromUserInput()
        val exerciseTotalTime: Time = getExerciseTotalTimeFromUserInput()

        return Exercise(exerciseName, exerciseTotalTime)
    }

    private fun getExerciseNameFromUserInput(): String {
        return new_exercise_name.editText!!.text.toString()
    }

    private fun getExerciseTotalTimeFromUserInput(): Time {
        val hours = hours_new_exercise.editText!!.text.toString().toLong()
        val minutes = minutes_new_exercise.editText!!.text.toString().toLong()
        val seconds = seconds_new_exercise.editText!!.text.toString().toLong()

        return Time(hours, minutes, seconds)
    }

    private fun addExerciseToSeries(exercise: Exercise) {
        seriesBeingCreated.addExercise(exercise)

        updateExerciseList()
    }

    private fun setupSaveSeriesButton() {
        save_new_series_button.setOnClickListener {
            doAsync {
                val db = SeriesDB.getDatabase(this@CreateSeriesActivity)
                db.getAccessObject().insertSeries(seriesBeingCreated)
            }
            val changeToHomepageActivity = Intent(this, HomepageActivity::class.java)

            Toast.makeText(
                this@CreateSeriesActivity,
                "Atividade salva com sucesso!",
                Toast.LENGTH_LONG
            ).show()
            startActivity(changeToHomepageActivity)
            finish()
        }
    }

    fun deleteExerciseFromSeries(exercise: Exercise) {
        seriesBeingCreated.removeExercise(exercise)
        updateExerciseList()
    }

    fun getSeriesToBeCreated(): Series {
        return seriesBeingCreated
    }

    fun updateExerciseList() {
        list_of_exercises.apply {
            layoutManager = LinearLayoutManager(this@CreateSeriesActivity)
            adapter = ExerciseAdapter(this@CreateSeriesActivity)
        }
    }

    override fun onResume() {
        super.onResume()

        loadActivityList()
    }

    private fun loadActivityList() {
        doAsync {
            val db = SeriesDB.getDatabase(this@CreateSeriesActivity)
            val m = db.getAccessObject().getAllCreatedSeries()

            Log.v("Appersonal", "Tamanho da lista = ${m.size}")
        }
    }
}
