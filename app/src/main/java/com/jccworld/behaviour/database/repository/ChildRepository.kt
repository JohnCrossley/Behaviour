package com.jccworld.behaviour.database.repository

import com.jccworld.behaviour.database.dao.ChildDao
import com.jccworld.behaviour.database.entity.ChildEntity
import com.jccworld.behaviour.domain.Child
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChildRepository @Inject constructor(private val childDao: ChildDao) {
    fun count(): Single<Int> = childDao.count()

    fun needToAddChildren(): Single<Boolean> = childDao.count().map { count -> count == 0 }

    fun all(): Flowable<List<Child>> = childDao
            .all()
            .map { listChildEntity -> listChildEntity.map { Child(it.id, it.firstName) } }


    fun add(child: Child) = childDao
            .insertChild(ChildEntity(firstName = child.firstName))

}
