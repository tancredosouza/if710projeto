package com.example.appersonaltrainer.view_model

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appersonaltrainer.activities.HomepageActivity
import com.example.appersonaltrainer.adapters.SeriesAdapter
import com.example.appersonaltrainer.components.Series
import com.example.appersonaltrainer.databases.SeriesDB
import kotlinx.android.synthetic.main.homepage_activity.series_list
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HomepageViewModel(
    private val context: Context,
    private val homepageActivity: HomepageActivity
) {
    fun deleteSeries(series: Series) {
        val db = SeriesDB.getDatabase(context)

        doAsync {
            db.getAccessObject().removeSeries(series)
        }

        loadSeriesOfUser()
    }

    init {
        loadSeriesOfUser()
    }

    fun loadSeriesOfUser() {
        val db = SeriesDB.getDatabase(context)

        doAsync {
            val items = db.getAccessObject().getAllCreatedSeries()

            uiThread {
                updateSeriesList(items.toList())
            }
        }
    }

    fun updateSeriesList(seriesList: List<Series>) {
        homepageActivity.series_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SeriesAdapter(seriesList, context, this@HomepageViewModel)
        }
    }
}
