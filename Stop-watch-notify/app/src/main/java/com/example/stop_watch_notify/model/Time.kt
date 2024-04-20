package com.example.stop_watch_notify.model

class Time(
    var hours: Int,
    var minutes: Int,
    var seconds: Int
) {
    fun value(): Long {
        return (hours.toLong() * 60 + minutes.toLong()) * 60 + seconds.toLong()
    }

    override fun toString(): String {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}