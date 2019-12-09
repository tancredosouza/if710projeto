package com.example.appersonaltrainer.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appersonaltrainer.model.SeriesHistory

@Dao
interface SeriesHistoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSeriesHistory(vararg seriesHistory: SeriesHistory)


    @Delete
    fun removeSeriesHistory(vararg seriesHistory: SeriesHistory)

    @Query("SELECT * FROM serieshistory")
    fun getAllCreatedSeriesHistory() : Array<SeriesHistory>
}
