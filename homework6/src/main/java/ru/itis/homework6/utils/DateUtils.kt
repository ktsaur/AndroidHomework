package ru.itis.homework6.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formateDate(time: Long): String{
    val string = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return string.format(Date(time))
}

fun parseDate(dateString: String): Long? {
    return try {
        val string = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        string.parse(dateString)?.time
    } catch (e: Exception) {
        Log.e("DATE UTILS--TAG","Не удолось распарсить дату")
        null
    }
}