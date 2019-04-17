package com.jccword.behaviour.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jccword.behaviour.database.entity.ChildBehaviourRecordEntity
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

}
