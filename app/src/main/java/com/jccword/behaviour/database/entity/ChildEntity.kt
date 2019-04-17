package com.jccword.behaviour.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "children")
data class ChildEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val firstName: String,
    val lastName: String? = null
)
