package com.jccworld.behaviour.ext

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDate
import java.util.*

class LocalDateTest {

    @Before
    fun setUp() {
    }

    /**
     * UK Week runs from Monday-Sunday
     */
    @Test
    fun calculatesStartOfWeekUk() {
        // init
        Locale.setDefault(Locale.UK)
        val sut = LocalDate.of(2019, 1, 16) //Wednesday
        val monday = LocalDate.of(2019, 1, 14)

        // run+verify
        assertEquals(monday, sut.startOfWeek())
    }

    @Test
    fun calculatesEndOfWeekUk() {
        // init
        Locale.setDefault(Locale.UK)
        val sut = LocalDate.of(2019, 1, 16) //Wednesday
        val sunday = LocalDate.of(2019, 1, 20)

        // run+verify
        assertEquals(sunday, sut.endOfWeek())
    }

    /**
     * USA Week runs from Sunday-Saturday
     */
    @Test
    fun calculatesStartOfWeekUsa() {
        // init
        Locale.setDefault(Locale.US)
        val sut = LocalDate.of(2019, 1, 16) //Wednesday
        val sunday = LocalDate.of(2019, 1, 13)

        // run+verify
        assertEquals(sunday, sut.startOfWeek())
    }

    @Test
    fun calculatesEndOfWeekUsa() {
        // init
        Locale.setDefault(Locale.US)
        val sut = LocalDate.of(2019, 1, 16) //Wednesday
        val saturday = LocalDate.of(2019, 1, 19)

        // run+verify
        assertEquals(saturday, sut.endOfWeek())
    }

}