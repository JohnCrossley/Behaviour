package com.jccword.behaviour.database.repository

import com.jccword.behaviour.database.dao.ChildBehaviourRecordDao
import com.jccword.behaviour.database.entity.ChildBehaviourRecordEntity
import com.jccword.behaviour.domain.*
import com.jccword.behaviour.ext.endOfDayEpoch
import com.jccword.behaviour.ext.endOfWeek
import com.jccword.behaviour.ext.startOfDayEpoch
import com.jccword.behaviour.ext.startOfWeek
import io.reactivex.Single
import org.threeten.bp.LocalDate
import java.lang.IllegalArgumentException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChildBehaviourRecordRepository @Inject constructor(private val childBehaviourRecordDao: ChildBehaviourRecordDao) {
    fun count(): Single<Int> = childBehaviourRecordDao.count()

    fun add(recording: Recording): Single<List<Long>> {
        val list = Array(recording.children.size) {
            ChildBehaviourRecordEntity(
                    dichotomy = recording.dichotomy,
                    childId = recording.children[it].id,
                    behaviourId = recording.behaviourId,
                    stars = recording.stars,
                    created = Calendar.getInstance())
        }

        return childBehaviourRecordDao.insert(*list)
    }

    fun thisWeek(childIds: List<Long>): Single<Map<Long, List<ChildBehaviourRecordEntity>>> {
        val start = LocalDate.now().startOfWeek()
        val end = LocalDate.now().endOfWeek()

        return childBehaviourRecordDao.period(childIds, start.startOfDayEpoch(), end.endOfDayEpoch()).map {
            val map = mutableMapOf<Long, MutableList<ChildBehaviourRecordEntity>>()
            it.forEach { record -> map[record.childId]?.add(record) ?: map.put(record.childId, mutableListOf(record)) }
            map.toMap()
        }
    }

}
