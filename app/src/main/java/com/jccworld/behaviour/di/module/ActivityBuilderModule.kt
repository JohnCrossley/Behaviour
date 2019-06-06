package com.jccworld.behaviour.di.module

import com.jccworld.behaviour.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class, NavigationModule::class, UiModule::class, MainActivityFragmentBuilderModule::class])
    internal abstract fun mainActivity(): MainActivity

}
