package com.jccworld.behaviour.database

import androidx.room.TypeConverter
import com.jccworld.behaviour.domain.Dichotomy
import java.util.*

class TypeConverters {

    @TypeConverter
    fun fromDichotomy(value: Dichotomy): String {
        return value.name
    }

    @TypeConverter
    fun toDichotomy(dichotomy: String): Dichotomy {
        return Dichotomy.valueOf(dichotomy)
    }

    @TypeConverter
    fun fromCalendar(value: Calendar): Long {
        return value.timeInMillis
    }

    @TypeConverter
    fun toCalendar(value: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = value
        return calendar
    }

}