package com.example.appersonaltrainer.view_holders

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.activities.CreateSeriesActivity
import com.example.appersonaltrainer.components.Exercise

class ExerciseViewHolder(exerciseView: View) : RecyclerView.ViewHolder(exerciseView) {
    private val nameOfExerciseHolder: TextView
    private val timeOfExerciseHolder: TextView
    private val exerciseDeleteAction: Button

    init {
        nameOfExerciseHolder = itemView.findViewById(R.id.name_of_exercise)
        timeOfExerciseHolder = itemView.findViewById(R.id.time_of_exercise)
        exerciseDeleteAction = itemView.findViewById(R.id.item_action)
    }

    fun bind(exercise: Exercise, activity: CreateSeriesActivity) {
        bindTextInformationForExercise(exercise)

        exerciseDeleteAction.setOnClickListener {
            shouldDeleteExerciseFromSeries(exercise, activity)
        }
    }

    private fun bindTextInformationForExercise(exercise: Exercise) {
        nameOfExerciseHolder.text = exercise.type.toString()
        timeOfExerciseHolder.text = exercise.totalTime.toString()
    }

    private fun shouldDeleteExerciseFromSeries(exercise: Exercise, activity: CreateSeriesActivity) {
        activity.deleteExerciseFromSeries(exercise)
    }
}
