package com.jccword.behaviour.di.module

import androidx.room.Room
import com.jccword.behaviour.Application
import com.jccword.behaviour.database.Database
import com.jccword.behaviour.database.dao.BehaviourDao
import com.jccword.behaviour.database.dao.ChildBehaviourRecordDao
import com.jccword.behaviour.database.dao.ChildDao
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
