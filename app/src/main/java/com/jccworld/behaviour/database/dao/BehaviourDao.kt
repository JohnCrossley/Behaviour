package com.jccworld.behaviour.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jccworld.behaviour.database.entity.BehaviourEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface BehaviourDao {

    @Query("SELECT COUNT(*) FROM behaviour")
    fun count(): Single<Int>

    @Query("SELECT * FROM behaviour")
    fun all(): Flowable<List<BehaviourEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(behaviourEntity: BehaviourEntity): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBatch(vararg behaviourEntity: BehaviourEntity): Single<List<Long>>

}
