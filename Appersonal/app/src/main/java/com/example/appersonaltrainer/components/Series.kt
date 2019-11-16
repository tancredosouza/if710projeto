package com.example.appersonaltrainer.components

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.appersonaltrainer.converters.SeriesConverter
import kotlin.random.Random

@Entity(tableName = "series")
@TypeConverters(SeriesConverter::class)
class Series {
    var exercises: MutableList<Exercise>

    var name: String?

    @PrimaryKey
    var id = Random.nextInt().toString()

    init {
        exercises = ArrayList()
        name = null
    }

    fun addExercise(e: Exercise) {
        exercises.add(e)
    }

    fun removeExercise(e: Exercise) {
        exercises.remove(e)
    }
}
