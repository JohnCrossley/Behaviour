package com.jccworld.behaviour.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jccworld.behaviour.domain.Dichotomy

@Entity(tableName = "behaviour")
data class BehaviourEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val stars: Int,
    val dichotomy: Dichotomy = Dichotomy.BOTH
)
