package com.example.appersonaltrainer.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appersonaltrainer.MinMaxFilter
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.adapters.ExerciseAdapter
import com.example.appersonaltrainer.components.Exercise
import com.example.appersonaltrainer.components.ExerciseType
import com.example.appersonaltrainer.components.Series
import com.example.appersonaltrainer.components.Time
import com.example.appersonaltrainer.databases.SeriesDB
import kotlinx.android.synthetic.main.create_series_activity.add_new_exercise_button
import kotlinx.android.synthetic.main.create_series_activity.exercise_type_text_view
import kotlinx.android.synthetic.main.create_series_activity.hours_new_exercise
import kotlinx.android.synthetic.main.create_series_activity.list_of_exercises
import kotlinx.android.synthetic.main.create_series_activity.minutes_new_exercise
import kotlinx.android.synthetic.main.create_series_activity.new_exercise_button
import kotlinx.android.synthetic.main.create_series_activity.new_series_name
import kotlinx.android.synthetic.main.create_series_activity.save_new_series_button
import kotlinx.android.synthetic.main.create_series_activity.seconds_new_exercise
import org.jetbrains.anko.doAsync
import java.util.InvalidPropertiesFormatException

class CreateSeriesActivity : AppCompatActivity() {
    private val seriesBeingCreated: Series = Series()
    private var currExerciseType: ExerciseType = ExerciseType.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_series_activity)

        setupMinutesAndSecondsInputFields()
        setupButtons()
    }

    private fun setupMinutesAndSecondsInputFields() {
        filterUserInputForField(R.id.minutes_new_exercise)
        filterUserInputForField(R.id.seconds_new_exercise)
    }

    private fun filterUserInputForField(fieldId: Int) {
        val field = findViewById<EditText>(fieldId)
        field.filters = arrayOf(MinMaxFilter)
    }

    private fun setupButtons() {
        setupSetExerciseTypeButton()
        setupAddNewExerciseButton()
        setupSaveSeriesButton()
    }

    private fun setupSetExerciseTypeButton() {
        val clickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.new_exercise_button -> setupAndShowExerciseTypePopupMenu(view)
            }
        }

        new_exercise_button.setOnClickListener(clickListener)
    }

    private fun setupAddNewExerciseButton() {
        add_new_exercise_button.setOnClickListener {
            addExerciseFromUserInputToSeries()
        }
    }

    private fun addExerciseFromUserInputToSeries() {
        try {
            createAndAddExerciseToSeries()
        } catch (e: NumberFormatException) {
            showToastMessage(R.string.exercise_has_empty_time_field_error)
        } catch (e: InvalidPropertiesFormatException) {
            showToastMessage(R.string.no_exercise_type_error)
        }
    }

    private fun createAndAddExerciseToSeries() {
        val exercise = createExerciseFromUserInput()
        addExerciseToSeries(exercise)
    }

    private fun createExerciseFromUserInput(): Exercise {
        if (currExerciseType == ExerciseType.EMPTY) {
            throw InvalidPropertiesFormatException("")
        }

        val exerciseTotalTime: Time = getExerciseTotalTimeFromUserInput()
        val exerciseFromUserInput = Exercise(currExerciseType, exerciseTotalTime)

        clearAllInputFields()

        return exerciseFromUserInput
    }

    private fun clearAllInputFields() {
        currExerciseType = ExerciseType.EMPTY
        exercise_type_text_view.text = currExerciseType.toString()
        hours_new_exercise.text.clear()
        minutes_new_exercise.text.clear()
        seconds_new_exercise.text.clear()
    }

    private fun getExerciseTotalTimeFromUserInput(): Time {
        val hours = hours_new_exercise.text.toString().toLong()
        val minutes = minutes_new_exercise.text.toString().toLong()
        val seconds = seconds_new_exercise.text.toString().toLong()

        return Time(hours, minutes, seconds)
    }

    private fun addExerciseToSeries(exercise: Exercise) {
        seriesBeingCreated.addExercise(exercise)
        updateExerciseList()
    }

    private fun setupSaveSeriesButton() {
        save_new_series_button.setOnClickListener {
            seriesBeingCreated.name = new_series_name.text.toString()

            if (seriesBeingCreated.exercises.isEmpty()) {
                showToastMessage(R.string.series_has_no_exercise_error)
            } else if (!seriesBeingCreated.name.isNullOrEmpty()) {
                addSeriesToDatabase()
                toastUserAndReturnToHomepageActivity()
            } else {
                showToastMessage(R.string.series_has_no_name_error)
            }
        }
    }

    private fun addSeriesToDatabase() {
        doAsync {
            val db = SeriesDB.getDatabase(this@CreateSeriesActivity)
            db.getAccessObject().insertSeries(seriesBeingCreated)
        }
    }

    private fun toastUserAndReturnToHomepageActivity() {
        showToastMessage(R.string.series_saved_successfully)

        val changeToHomepageActivity = Intent(this, HomepageActivity::class.java)
        startActivity(changeToHomepageActivity)
        finish()
    }

    fun deleteExerciseFromSeries(exercise: Exercise) {
        seriesBeingCreated.removeExercise(exercise)
        updateExerciseList()
    }

    fun getSeriesToBeCreated(): Series {
        return seriesBeingCreated
    }

    private fun updateExerciseList() {
        list_of_exercises.apply {
            layoutManager = LinearLayoutManager(this@CreateSeriesActivity)
            adapter = ExerciseAdapter(this@CreateSeriesActivity)
        }
    }

    private fun setupAndShowExerciseTypePopupMenu(view: View) {
        val exerciseTypesPopupMenu = setupPopupMenu(view)

        exerciseTypesPopupMenu.show()
    }

    private fun setupPopupMenu(view: View): PopupMenu {
        val exerciseTypesPopupMenu = PopupMenu(this, view)
        exerciseTypesPopupMenu.inflate(R.menu.name_exercise_menu)

        exerciseTypesPopupMenu.setOnMenuItemClickListener { exerciseTypeItem: MenuItem? ->
            val exerciseType = exerciseTypeItem!!.title
            exercise_type_text_view.text = exerciseType

            when (exerciseTypeItem.itemId) {
                R.id.ciclismo_exercise -> {
                    currExerciseType = ExerciseType.CICLISMO
                }
                R.id.corrida_exercise -> {
                    currExerciseType = ExerciseType.CORRIDA
                }
                R.id.yoga_exercise -> {
                    currExerciseType = ExerciseType.YOGA
                }
                R.id.caminhada_exercise -> {
                    currExerciseType = ExerciseType.CAMINHADA
                }
            }

            true
        }

        return exerciseTypesPopupMenu
    }

    private fun showToastMessage(messageId: Int) {
        Toast.makeText(this, messageId, Toast.LENGTH_LONG).show()
    }
}
