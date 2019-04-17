package com.jccword.behaviour.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jccword.behaviour.domain.Dichotomy

@Entity(tableName = "behaviour")
data class BehaviourEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val name: String,
    val stars: Int,
    val dichotomy: Dichotomy = Dichotomy.BOTH
)
