package com.example.appersonaltrainer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.activities.HomepageActivity
import com.example.appersonaltrainer.components.Series
import com.example.appersonaltrainer.view_holders.SeriesViewHolder

class SeriesAdapter(private val series: List<Series>, private val activity: HomepageActivity) :
    RecyclerView.Adapter<SeriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val itemFeedViewInflater = setupInflaterForViewGroup(parent)
        return SeriesViewHolder(itemFeedViewInflater)
    }

    private fun setupInflaterForViewGroup(viewGroup: ViewGroup): View {
        return LayoutInflater.from(activity).inflate(R.layout.series_card, viewGroup, false)
    }

    override fun onBindViewHolder(seriesViewHolder: SeriesViewHolder, idx: Int) {
        val seriesOfInterest = series[idx]

        seriesViewHolder.bind(seriesOfInterest, activity)
    }

    override fun getItemCount() = series.size

}
