package com.dvt.weather_app.utils.time

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun toFormattedDateTime(time: Long): String {
    val format = SimpleDateFormat("yyyy MMM, HH:mm", Locale.getDefault())
    return format.format(Date(time))
}