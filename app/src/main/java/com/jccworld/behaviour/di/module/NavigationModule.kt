package com.jccworld.behaviour.di.module

import com.jccworld.behaviour.MainActivity
import com.jccworld.behaviour.ui.Navigation
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {

    @Provides
    fun provideNavigation(mainActivity: MainActivity) = Navigation(mainActivity.supportFragmentManager)

}
