package com.jccworld.behaviour.database

import com.jccworld.behaviour.database.dao.BehaviourDao
import com.jccworld.behaviour.database.entity.BehaviourEntity
import com.jccworld.behaviour.domain.Dichotomy
import javax.inject.Inject

class InitDatabase @Inject constructor(private val behaviourDao: BehaviourDao) {

    fun addDefaultBehaviours() = behaviourDao.insertBatch(
            BehaviourEntity(name = "Tried all food", stars = 1),
            BehaviourEntity(name = "Tidied up", stars = 1),
            BehaviourEntity(name = "Hitting/Pushing", stars = 1, dichotomy = Dichotomy.BAD),
            BehaviourEntity(name = "Crying", stars = 1, dichotomy = Dichotomy.BAD)
        )

}
