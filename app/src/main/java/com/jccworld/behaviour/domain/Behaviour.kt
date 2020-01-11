package com.jccworld.behaviour.domain

data class Behaviour(
        val id: Long? = 0,
        val dichotomy: Dichotomy,
        val name: String,
        val stars: Int
)
