package com.example.appersonaltrainer.converters

import androidx.room.TypeConverter
import com.example.appersonaltrainer.components.Exercise
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SeriesConverter {
    @TypeConverter
    fun toString(value: MutableList<Exercise>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromString(value: String?): MutableList<Exercise>? {
        val listType = object : TypeToken<MutableList<Exercise>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
