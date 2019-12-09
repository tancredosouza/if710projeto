package com.example.appersonaltrainer.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appersonaltrainer.fragments.HomepageFragment
import com.example.appersonaltrainer.adapters.SeriesAdapter
import com.example.appersonaltrainer.model.Series
import com.example.appersonaltrainer.databases.SeriesDB
import kotlinx.android.synthetic.main.fragment_homepage.series_list
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HomepageViewModel (
    private val context: Context,
    private val homepageFragment: HomepageFragment
) : ViewModel() {
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
        homepageFragment.series_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SeriesAdapter(seriesList, context, this@HomepageViewModel)
        }
    }
}
