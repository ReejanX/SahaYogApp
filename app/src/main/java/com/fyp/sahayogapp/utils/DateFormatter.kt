package com.fyp.sahayogapp.utils

import android.annotation.SuppressLint
import android.text.TextUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    @SuppressLint("SimpleDateFormat")
    fun convertDate(date: String): String {
        val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val formatter2: DateFormat = SimpleDateFormat("E, MMM dd")
        val newDate: Date = formatter.parse(TextUtils.substring(date, 0, 10))
        return formatter2.format(newDate)
    }
}