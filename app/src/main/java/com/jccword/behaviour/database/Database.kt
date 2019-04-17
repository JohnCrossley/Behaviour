package com.jccword.behaviour.database

import com.jccword.behaviour.database.dao.ChildDao
import com.jccword.behaviour.database.entity.ChildEntity

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jccword.behaviour.database.dao.BehaviourDao
import com.jccword.behaviour.database.dao.ChildBehaviourRecordDao
import com.jccword.behaviour.database.entity.BehaviourEntity
import com.jccword.behaviour.database.entity.ChildBehaviourRecordEntity

@androidx.room.Database(entities = [
        ChildEntity::class,
        BehaviourEntity::class,
        ChildBehaviourRecordEntity::class
    ],
    version = 1)
@TypeConverters(com.jccword.behaviour.database.TypeConverters::class)
abstract class Database : RoomDatabase() {

    abstract val childDao: ChildDao

    abstract val behaviourDao: BehaviourDao

    abstract val childBehaviourRecordDao: ChildBehaviourRecordDao

    companion object {
        internal val BEHAVIOUR_DB = "behaviour.db"
    }
    
}
