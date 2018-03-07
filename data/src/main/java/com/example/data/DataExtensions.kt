package com.example.data

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ayush on 3/6/18.
 */
data class DateTimePair(val date: String, val time: String)

fun Calendar.getDateTime() : DateTimePair {
    val sdfDate = SimpleDateFormat("d MMM", Locale.ENGLISH)
    val sdfTime = SimpleDateFormat("h:mm a", Locale.ENGLISH)
    return DateTimePair(sdfDate.format(this.time), sdfTime.format(this.time))
}