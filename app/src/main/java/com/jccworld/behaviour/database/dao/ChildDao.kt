package com.jccworld.behaviour.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jccworld.behaviour.database.entity.ChildEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ChildDao {

    @Query("SELECT COUNT(*) FROM children")
    fun count(): Single<Int>

    @Query("SELECT * FROM children ORDER BY id")
    fun all(): Flowable<List<ChildEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChild(child: ChildEntity): Single<Long>

}
