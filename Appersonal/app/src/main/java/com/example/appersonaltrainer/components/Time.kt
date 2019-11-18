package com.example.appersonaltrainer.components

data class Time(val hours: Long, val minutes: Long, val seconds: Long) {
    override fun toString(): String {
        return "$hours:$minutes:$seconds"
    }
}
