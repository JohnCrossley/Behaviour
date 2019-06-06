package com.jccworld.behaviour.domain

data class Recording(
        val dichotomy: Dichotomy,
        val children: List<Child>,
        var stars: Short = 0,
        var behaviourId: Long = 0)