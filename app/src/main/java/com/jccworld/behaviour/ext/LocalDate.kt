package com.jccworld.behaviour.ext

import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.temporal.WeekFields
import java.util.*

fun LocalDate.startOfWeek(): LocalDate = with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1L)

fun LocalDate.endOfWeek(): LocalDate = with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 7L)

fun LocalDate.startOfDayEpoch(): Long = this.atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

fun LocalDate.endOfDayEpoch(): Long = this.atStartOfDay(ZoneId.systemDefault())
        .plusHours(23)
        .plusMinutes(59)
        .plusSeconds(59)
        .toInstant()
        .toEpochMilli()
