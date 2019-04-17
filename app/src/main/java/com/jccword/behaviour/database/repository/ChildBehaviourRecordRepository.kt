package com.jccword.behaviour.database.repository

import com.jccword.behaviour.database.dao.ChildBehaviourRecordDao
import com.jccword.behaviour.database.entity.ChildBehaviourRecordEntity
import com.jccword.behaviour.domain.Recording
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChildBehaviourRecordRepository @Inject constructor(private val childBehaviourRecordDao: ChildBehaviourRecordDao) {
    fun count(): Single<Int> = childBehaviourRecordDao.count()

    fun add(recording: Recording): Single<List<Long>> {
        val list = Array(recording.childrenIds.size) {
            ChildBehaviourRecordEntity(
                    dichotomy = recording.dichotomy,
                    childId = recording.childrenIds[it],
                    behaviourId = recording.behaviourId,
                    created = Calendar.getInstance())
        }

        return childBehaviourRecordDao.insert(*list)
    }
}
