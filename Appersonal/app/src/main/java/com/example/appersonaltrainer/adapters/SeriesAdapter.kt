package com.example.appersonaltrainer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appersonaltrainer.R
import com.example.appersonaltrainer.model.Series
import com.example.appersonaltrainer.view_holders.SeriesViewHolder
import com.example.appersonaltrainer.view_model.HomepageViewModel

class SeriesAdapter(
    private val series: List<Series>?,
    private val context: Context,
    private val homepageViewModel: HomepageViewModel
) :
    RecyclerView.Adapter<SeriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val itemFeedViewInflater = setupInflaterForViewGroup(parent)
        return SeriesViewHolder(itemFeedViewInflater, homepageViewModel)
    }

    private fun setupInflaterForViewGroup(viewGroup: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.series_card, viewGroup, false)
    }

    override fun onBindViewHolder(seriesViewHolder: SeriesViewHolder, idx: Int) {
        val seriesOfInterest = series!![idx]

        seriesViewHolder.bind(seriesOfInterest)
    }

    override fun getItemCount() = series!!.size
}
