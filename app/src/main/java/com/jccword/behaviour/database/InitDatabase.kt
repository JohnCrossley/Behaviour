package com.jccword.behaviour.database

import com.jccword.behaviour.database.dao.BehaviourDao
import com.jccword.behaviour.database.entity.BehaviourEntity
import com.jccword.behaviour.domain.Dichotomy
import io.reactivex.Completable
import javax.inject.Inject

class InitDatabase @Inject constructor(private val behaviourDao: BehaviourDao) {

    fun addDefaultBehaviours() = behaviourDao.insertBatch(
            BehaviourEntity(name = "Tried all food", stars = 1),
            BehaviourEntity(name = "Tidied up", stars = 1),
            BehaviourEntity(name = "Hitting/Pushing", stars = 1, dichotomy = Dichotomy.BAD),
            BehaviourEntity(name = "Crying", stars = 1, dichotomy = Dichotomy.BAD)
        )

}
