package com.example.appersonaltrainer.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appersonaltrainer.model.Series

@Dao
interface SeriesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSeries(vararg series: Series)

    @Update
    fun updateSeries(vararg series: Series)

    @Delete
    fun removeSeries(vararg series: Series)

    @Query("SELECT * FROM series")
    fun getAllCreatedSeries() : Array<Series>

    @Query("SELECT * FROM series WHERE id=:id ")
    fun getSeriesWithId(id: String): Series
}
