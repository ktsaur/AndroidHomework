package ru.itis.homework6.data.db.converters

import androidx.room.TypeConverter
import java.util.Date

class DateConverters {
    @TypeConverter
    fun fromDateToLong(input: Date?): Long? {
        return input?.time
    }

    @TypeConverter
    fun fromLongToDate(input: Long?): Date? {
        return input?.let {
            Date(it)
        }
    }

}