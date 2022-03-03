package com.fyp.sahayogapp.utils

import android.annotation.SuppressLint
import android.text.TextUtils
import java.text.DateFormat
import java.text.ParseException
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

    @SuppressLint("SimpleDateFormat")
    fun convertToDBDate(date: String): String {
        val formatter: DateFormat = SimpleDateFormat("EEE MMM dd hh:mm:ss 'GMT'Z yyyy")
        val formatter2: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val newDate: Date = formatter.parse(date)
        return formatter2.format(newDate)
    }


    fun getDateParsed(date: String?, pattern: String?): String? {
        val dateFormat = SimpleDateFormat(pattern)
        var d: Date? = null
        try {
            d = dateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        dateFormat.applyPattern("EEE MMM d, yyyy")
        return dateFormat.format(d).substring(4, dateFormat.format(d).length)
    }
}