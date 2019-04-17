package com.jccword.behaviour.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jccword.behaviour.domain.Dichotomy
import java.util.*

@Entity(tableName = "child_behaviour_record")
data class ChildBehaviourRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val dichotomy: Dichotomy,

    val childId: Long,

    val behaviourId: Long,

    val created: Calendar
)
