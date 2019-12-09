package com.example.appersonaltrainer.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appersonaltrainer.fragments.RecentsFragment
import com.example.appersonaltrainer.adapters.SeriesHistoryAdapter
import com.example.appersonaltrainer.model.SeriesHistory
import com.example.appersonaltrainer.databases.SeriesHistoryDB
import kotlinx.android.synthetic.main.fragment_recents.series_history_list
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class RecentsViewModel (
    private val context: Context,
    private val recentsFragment: RecentsFragment
) : ViewModel() {
    fun deleteSeriesHistory(series_history: SeriesHistory) {
        val db = SeriesHistoryDB.getDatabase(context)

        doAsync {
            db.getAccessObject().removeSeriesHistory(series_history)
        }

        loadSeriesHistoryOfUser()
    }

    init {
        loadSeriesHistoryOfUser()
    }

    fun loadSeriesHistoryOfUser() {
        val db = SeriesHistoryDB.getDatabase(context)

        doAsync {
            val items = db.getAccessObject().getAllCreatedSeriesHistory()

            uiThread {
                updateSeriesHistoryList(items.toList().reversed())
            }
        }
    }

    fun updateSeriesHistoryList(series_history_List: List<SeriesHistory>) {
        recentsFragment.series_history_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SeriesHistoryAdapter(series_history_List, context, this@RecentsViewModel)
        }
    }
}
