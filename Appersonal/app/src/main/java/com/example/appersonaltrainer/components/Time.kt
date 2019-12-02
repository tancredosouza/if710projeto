package com.example.appersonaltrainer.components

data class Time(val hours: Long, val minutes: Long, val seconds: Long) {
    override fun toString(): String {
        return "${hours.toString().padStart(2, '0')}:" +
            "${minutes.toString().padStart(2, '0')}:" +
            seconds.toString().padStart(2, '0')
    }

    fun toSeconds(): Long {
        return seconds + minutes * 60 + hours * 60 * 60
    }
}
