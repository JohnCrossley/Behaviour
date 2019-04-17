package com.jccword.behaviour.di.component

import com.jccword.behaviour.Application
import com.jccword.behaviour.database.InitDatabase
import com.jccword.behaviour.database.repository.BehaviourRepository
import com.jccword.behaviour.database.repository.ChildRepository
import com.jccword.behaviour.database.repository.ChildBehaviourRecordRepository
import com.jccword.behaviour.di.module.ActivityBuilderModule
import com.jccword.behaviour.di.module.ApplicationModule
import com.jccword.behaviour.di.module.DataModule
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

}

