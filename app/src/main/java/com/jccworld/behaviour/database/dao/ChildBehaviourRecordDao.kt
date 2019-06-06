package com.jccworld.behaviour.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jccworld.behaviour.database.entity.ChildBehaviourRecordEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ChildBehaviourRecordDao {

    @Query("SELECT COUNT(*) FROM child_behaviour_record")
    fun count(): Single<Int>

    @Query("SELECT * FROM child_behaviour_record")
    fun all(): Flowable<List<ChildBehaviourRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg childBehaviourRecordEntity: ChildBehaviourRecordEntity): Single<List<Long>>

    @Query("SELECT * FROM child_behaviour_record WHERE childId IN (:childIds) AND created >= :startDateTime AND created <= :endDateTime ORDER BY childId, created")
    fun period(childIds: List<Long>, startDateTime: Long, endDateTime: Long): Single<List<ChildBehaviourRecordEntity>>

}
