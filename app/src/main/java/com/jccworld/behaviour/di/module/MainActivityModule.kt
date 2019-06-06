package com.jccworld.behaviour.di.module

import android.content.Context
import com.jccworld.behaviour.MainActivity
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {

    @Binds
    abstract fun bindContext(mainActivity: MainActivity): Context

}
