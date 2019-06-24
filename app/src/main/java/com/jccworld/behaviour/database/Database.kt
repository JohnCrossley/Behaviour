package com.jccworld.behaviour.database

import com.jccworld.behaviour.database.dao.ChildDao
import com.jccworld.behaviour.database.entity.ChildEntity

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jccworld.behaviour.database.dao.BehaviourDao
import com.jccworld.behaviour.database.dao.ChildBehaviourRecordDao
import com.jccworld.behaviour.database.entity.BehaviourEntity
import com.jccworld.behaviour.database.entity.ChildBehaviourRecordEntity

@androidx.room.Database(entities = [
        ChildEntity::class,
        BehaviourEntity::class,
        ChildBehaviourRecordEntity::class
    ],
    version = 1)
@TypeConverters(com.jccworld.behaviour.database.TypeConverters::class)
abstract class Database : RoomDatabase() {

    abstract val childDao: ChildDao

    abstract val behaviourDao: BehaviourDao

    abstract val childBehaviourRecordDao: ChildBehaviourRecordDao

    companion object {
        internal const val BEHAVIOUR_DB = "behaviour.db"
    }
    
}
