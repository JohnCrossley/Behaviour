package com.jccworld.behaviour.di.component

import com.jccworld.behaviour.Application
import com.jccworld.behaviour.database.InitDatabase
import com.jccworld.behaviour.database.repository.BehaviourRepository
import com.jccworld.behaviour.database.repository.ChildRepository
import com.jccworld.behaviour.database.repository.ChildBehaviourRecordRepository
import com.jccworld.behaviour.di.module.ActivityBuilderModule
import com.jccworld.behaviour.di.module.ApplicationModule
import com.jccworld.behaviour.di.module.DataModule
import com.jccworld.behaviour.domain.UserSession
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    DataModule::class,
    ActivityBuilderModule::class
])
@Singleton
interface ApplicationComponent: AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(app: Application): Builder

        fun build(): ApplicationComponent

    }

    fun getChildRepository(): ChildRepository

    fun getBehaviourRepository(): BehaviourRepository

    fun getChildBehaviourRecordRepository(): ChildBehaviourRecordRepository

    fun getInitDatabase(): InitDatabase

    fun getUserSession(): UserSession

}

