package com.example.appersonaltrainer.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appersonaltrainer.model.Series
import com.example.appersonaltrainer.daos.SeriesDAO

@Database(entities= arrayOf(Series::class), version=1)
abstract class SeriesDB : RoomDatabase() {
    abstract fun getAccessObject(): SeriesDAO

    companion object {
        private var INSTANCE: SeriesDB? = null
        fun getDatabase(ctx: Context): SeriesDB {
            if (INSTANCE == null) {
                synchronized(SeriesDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        ctx.applicationContext,
                        SeriesDB::class.java,
                        "series.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}
