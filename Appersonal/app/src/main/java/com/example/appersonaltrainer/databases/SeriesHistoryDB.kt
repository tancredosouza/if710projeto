package com.example.appersonaltrainer.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appersonaltrainer.daos.SeriesHistoryDAO
import com.example.appersonaltrainer.model.SeriesHistory

@Database(entities= arrayOf(SeriesHistory::class), version=1)
abstract class SeriesHistoryDB : RoomDatabase() {
    abstract fun getAccessObject(): SeriesHistoryDAO

    companion object {
        private var INSTANCE: SeriesHistoryDB? = null
        fun getDatabase(ctx: Context): SeriesHistoryDB {
            if (INSTANCE == null) {
                synchronized(SeriesHistoryDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        ctx.applicationContext,
                        SeriesHistoryDB::class.java,
                        "serieshistory.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}
