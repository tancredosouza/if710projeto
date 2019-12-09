package com.example.appersonaltrainer.view_holders

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.activities.SeriesHappeningActivity
import com.example.appersonaltrainer.model.Series
import com.example.appersonaltrainer.model.SeriesHistory
import com.example.appersonaltrainer.view_model.RecentsViewModel
import kotlinx.android.synthetic.main.series_history_card.view.*

class SeriesHistoryViewHolder(
    private val seriesHistoryView: View,
    private val recentsViewModel: RecentsViewModel
) :
    RecyclerView.ViewHolder(seriesHistoryView) {

    fun bind(seriesHistory: SeriesHistory) {
        bindTextInformationForSeries(seriesHistory)

        itemView.delete_action.setOnClickListener {
            recentsViewModel.deleteSeriesHistory(seriesHistory)
        }
    }

    private fun bindTextInformationForSeries(seriesHistory: SeriesHistory) {
        itemView.series_name.text = seriesHistory.name
        itemView.series_timestamp.text = seriesHistory.date
    }
}
