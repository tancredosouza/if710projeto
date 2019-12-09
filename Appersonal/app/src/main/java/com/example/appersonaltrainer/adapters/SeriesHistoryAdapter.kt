package com.example.appersonaltrainer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.model.Series
import com.example.appersonaltrainer.model.SeriesHistory
import com.example.appersonaltrainer.view_holders.SeriesHistoryViewHolder
import com.example.appersonaltrainer.view_holders.SeriesViewHolder
import com.example.appersonaltrainer.view_model.RecentsViewModel

class SeriesHistoryAdapter(
    private val series: List<SeriesHistory>?,
    private val context: Context,
    private val recentsViewModel: RecentsViewModel
) :
    RecyclerView.Adapter<SeriesHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesHistoryViewHolder {
        val itemFeedViewInflater = setupInflaterForViewGroup(parent)
        return SeriesHistoryViewHolder(itemFeedViewInflater, recentsViewModel)
    }

    private fun setupInflaterForViewGroup(viewGroup: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.series_history_card, viewGroup, false)
    }

    override fun onBindViewHolder(seriesHistoryViewHolder: SeriesHistoryViewHolder, idx: Int) {
        val seriesOfInterest = series!![idx]

        seriesHistoryViewHolder.bind(seriesOfInterest)
    }

    override fun getItemCount() = series!!.size
}
