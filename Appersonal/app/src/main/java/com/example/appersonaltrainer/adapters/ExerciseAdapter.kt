package com.example.appersonaltrainer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.activities.CreateSeriesActivity
import com.example.appersonaltrainer.components.Series
import com.example.appersonaltrainer.view_holders.ExerciseViewHolder

class ExerciseAdapter(private val activity: CreateSeriesActivity) :
    RecyclerView.Adapter<ExerciseViewHolder>() {
    private val series: Series = activity.getSeriesToBeCreated()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemFeedViewInflater = setupInflaterForViewGroup(parent)
        return ExerciseViewHolder(itemFeedViewInflater)
    }

    private fun setupInflaterForViewGroup(viewGroup: ViewGroup): View {
        return LayoutInflater.from(activity).inflate(R.layout.exercise_of_series, viewGroup, false)
    }

    override fun onBindViewHolder(exerciseViewHolder: ExerciseViewHolder, idx: Int) {
        val exercisesInSeries = series.exercises[idx]

        exerciseViewHolder.bind(exercisesInSeries, activity)
    }

    override fun getItemCount() = series.exercises.size

}
