package com.jccworld.behaviour.domain

import java.util.*

data class Recording(
        val dichotomy: Dichotomy,
        val children: List<Child>,
        var stars: Short = 0,
        var time: Calendar = Calendar.getInstance(),
        var behaviourId: Long = 0
)