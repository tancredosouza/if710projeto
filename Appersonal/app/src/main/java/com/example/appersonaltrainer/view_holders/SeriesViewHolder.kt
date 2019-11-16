package com.example.appersonaltrainer.view_holders

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.activities.HomepageActivity
import com.example.appersonaltrainer.components.Series

class SeriesViewHolder(seriesView: View) : RecyclerView.ViewHolder(seriesView) {
    private val nameOfSeriesHolder: TextView
    private val seriesDeleteAction: Button

    init {
        nameOfSeriesHolder = itemView.findViewById(R.id.series_name)
        seriesDeleteAction = itemView.findViewById(R.id.delete_action)
    }

    fun bind(series : Series, activity: HomepageActivity) {
        bindTextInformationForSeries(series)

        seriesDeleteAction.setOnClickListener {
            shouldDeleteSeries(series, activity)
        }
    }

    private fun bindTextInformationForSeries(series: Series) {
        nameOfSeriesHolder.text = series.name
    }

    private fun shouldDeleteSeries(series: Series, activity: HomepageActivity) {
        activity.deleteSeries(series)
    }
}
