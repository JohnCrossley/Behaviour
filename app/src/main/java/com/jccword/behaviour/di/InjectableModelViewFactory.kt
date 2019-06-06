package com.jccword.behaviour.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jccword.behaviour.Application
import com.jccword.behaviour.BootViewModel
import com.jccword.behaviour.MainActivity
import com.jccword.behaviour.addchild.AddChildViewModel
import com.jccword.behaviour.dashboard.DashboardViewModel
import com.jccword.behaviour.database.InitDatabase
import com.jccword.behaviour.database.repository.BehaviourRepository
import com.jccword.behaviour.database.repository.ChildRepository
import com.jccword.behaviour.database.repository.ChildBehaviourRecordRepository
import com.jccword.behaviour.di.component.DaggerInjectableModelViewFactoryComponent
import com.jccword.behaviour.domain.UserSession
import com.jccword.behaviour.managechildren.ManageChildrenViewModel
import com.jccword.behaviour.summary.SummaryViewModel
import com.jccword.behaviour.what.WhatViewModel
import com.jccword.behaviour.who.WhoViewModel
import javax.inject.Inject

class InjectableModelViewFactory @Inject constructor(application: Application, mainActivity: MainActivity) : ViewModelProvider.Factory {
    @Inject
    lateinit var childRepository: ChildRepository

    @Inject
    lateinit var behaviourRepository: BehaviourRepository

    @Inject
    lateinit var childBehaviourRecordRepository: ChildBehaviourRecordRepository

    @Inject
    lateinit var userSession: UserSession

    @Inject
    lateinit var initDatabase: InitDatabase

    init {
        DaggerInjectableModelViewFactoryComponent.builder()
                .application(application)
                .mainActivity(mainActivity)
                .appComponent(application.component())
                .build()
                .inject(this)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(BootViewModel::class.java) -> BootViewModel(childRepository) as T
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> DashboardViewModel() as T
            modelClass.isAssignableFrom(ManageChildrenViewModel::class.java) -> ManageChildrenViewModel(childRepository) as T
            modelClass.isAssignableFrom(AddChildViewModel::class.java) -> AddChildViewModel(childRepository) as T
            modelClass.isAssignableFrom(WhoViewModel::class.java) -> WhoViewModel(userSession, childRepository) as T
            modelClass.isAssignableFrom(WhatViewModel::class.java) -> WhatViewModel(userSession, behaviourRepository, initDatabase) as T
            modelClass.isAssignableFrom(SummaryViewModel::class.java) -> SummaryViewModel(userSession, childBehaviourRecordRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel: $modelClass")
        }
    }

}
