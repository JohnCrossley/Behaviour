package com.jccworld.behaviour.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jccworld.behaviour.Application
import com.jccworld.behaviour.BootViewModel
import com.jccworld.behaviour.MainActivity
import com.jccworld.behaviour.addchild.AddChildViewModel
import com.jccworld.behaviour.dashboard.DashboardViewModel
import com.jccworld.behaviour.database.InitDatabase
import com.jccworld.behaviour.database.repository.BehaviourRepository
import com.jccworld.behaviour.database.repository.ChildRepository
import com.jccworld.behaviour.database.repository.ChildBehaviourRecordRepository
import com.jccworld.behaviour.di.component.DaggerInjectableModelViewFactoryComponent
import com.jccworld.behaviour.domain.UserSession
import com.jccworld.behaviour.managechildren.ManageChildrenViewModel
import com.jccworld.behaviour.debug.DebugViewModel
import com.jccworld.behaviour.summary.SummaryViewModel
import com.jccworld.behaviour.what.WhatViewModel
import com.jccworld.behaviour.who.WhoViewModel
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
            modelClass.isAssignableFrom(DebugViewModel::class.java) -> DebugViewModel(childRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel: $modelClass")
        }
    }

}
