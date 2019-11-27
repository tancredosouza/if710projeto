package com.example.appersonaltrainer.view_holders

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.model.Series
import com.example.appersonaltrainer.view_model.HomepageViewModel

class SeriesViewHolder(seriesView: View, private val homepageViewModel: HomepageViewModel) :
    RecyclerView.ViewHolder(seriesView) {

    private val nameOfSeriesHolder: TextView
    private val seriesDeleteAction: Button

    init {
        nameOfSeriesHolder = itemView.findViewById(R.id.series_name)
        seriesDeleteAction = itemView.findViewById(R.id.delete_action)
    }

    fun bind(series: Series) {
        bindTextInformationForSeries(series)

        seriesDeleteAction.setOnClickListener {
            homepageViewModel.deleteSeries(series)
        }
    }

    private fun bindTextInformationForSeries(series: Series) {
        nameOfSeriesHolder.text = series.name
    }
}
