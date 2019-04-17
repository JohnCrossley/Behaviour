package com.jccword.behaviour.di.module

import com.jccword.behaviour.MainActivity
import com.jccword.behaviour.ui.Navigation
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {

    @Provides
    fun provideNavigation(mainActivity: MainActivity) = Navigation(mainActivity.supportFragmentManager)

}
