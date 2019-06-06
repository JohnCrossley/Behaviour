package com.jccworld.behaviour.database.repository

import com.jccworld.behaviour.database.dao.BehaviourDao
import com.jccworld.behaviour.database.entity.BehaviourEntity
import com.jccworld.behaviour.domain.Behaviour
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BehaviourRepository @Inject constructor(private val behaviourDao: BehaviourDao) {
    fun count(): Single<Int> = behaviourDao.count()

    fun all(): Flowable<List<Behaviour>> = behaviourDao
            .all()
            .map { listBehaviourEntity -> listBehaviourEntity.map { Behaviour(it.id, it.dichotomy, it.name, it.stars) } }


    fun add(behaviour: Behaviour) = behaviourDao
            .insert(BehaviourEntity(dichotomy = behaviour.dichotomy, name = behaviour.name, stars = behaviour.stars))

}
