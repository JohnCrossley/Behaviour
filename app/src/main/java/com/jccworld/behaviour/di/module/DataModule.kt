package com.jccworld.behaviour.di.module

import androidx.room.Room
import com.jccworld.behaviour.Application
import com.jccworld.behaviour.database.Database
import com.jccworld.behaviour.database.dao.BehaviourDao
import com.jccworld.behaviour.database.dao.ChildBehaviourRecordDao
import com.jccworld.behaviour.database.dao.ChildDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): Database =
            Room.databaseBuilder(application, Database::class.java, Database.BEHAVIOUR_DB)
                    .fallbackToDestructiveMigration()
                    .build()

    @Provides
    @Singleton
    internal fun provideChildDao(database: Database): ChildDao = database.childDao

    @Provides
    @Singleton
    internal fun provideBehaviourDao(database: Database): BehaviourDao = database.behaviourDao

    @Provides
    @Singleton
    internal fun provideChildBehaviourRecordDao(database: Database): ChildBehaviourRecordDao = database.childBehaviourRecordDao

}
