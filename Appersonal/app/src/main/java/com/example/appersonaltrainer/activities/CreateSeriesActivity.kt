package com.example.appersonaltrainer.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.adapters.ExerciseAdapter
import com.example.appersonaltrainer.components.Exercise
import com.example.appersonaltrainer.components.Series
import com.example.appersonaltrainer.databases.SeriesDB
import kotlinx.android.synthetic.main.create_series_activity.add_new_exercise_button
import kotlinx.android.synthetic.main.create_series_activity.list_of_exercises
import kotlinx.android.synthetic.main.create_series_activity.new_exercise_name
import kotlinx.android.synthetic.main.create_series_activity.new_series_name
import kotlinx.android.synthetic.main.create_series_activity.save_new_series_button
import kotlinx.android.synthetic.main.create_series_activity.time_new_exercise
import org.jetbrains.anko.doAsync

class CreateSeriesActivity : AppCompatActivity() {
    private val seriesBeingCreated: Series = Series()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_series_activity)

        setupButtons()
    }

    private fun setupButtons() {
        setupAddNewExerciseButton()
        setupSaveSeriesButton()
    }

    private fun setupAddNewExerciseButton() {
        add_new_exercise_button.setOnClickListener {
            convertUserInputExerciseAndAddToSeries()
        }
    }

    private fun convertUserInputExerciseAndAddToSeries() {
        val exercise  = createExerciseFromUserInput()
        addExerciseToSeries(exercise)

        seriesBeingCreated.name = new_series_name.editText!!.text.toString()
    }

    private fun createExerciseFromUserInput(): Exercise {
        val exerciseName: String = getExerciseNameFromUserInput()
        val exerciseDuration: Long = getDurationOfExerciseFromUserInput()

        return Exercise(exerciseName, exerciseDuration)
    }

    private fun getExerciseNameFromUserInput(): String {
        return new_exercise_name.editText!!.text.toString()
    }

    private fun getDurationOfExerciseFromUserInput(): Long {
        return time_new_exercise.editText!!.text.toString().toLong()
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

            Toast.makeText(this@CreateSeriesActivity, "Atividade salva com sucesso!", Toast.LENGTH_LONG).show()
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
