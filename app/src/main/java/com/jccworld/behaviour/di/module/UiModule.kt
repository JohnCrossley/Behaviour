package com.jccworld.behaviour.di.module

import com.jccworld.behaviour.MainActivity
import com.jccworld.behaviour.ui.ProgressUi
import com.jccworld.behaviour.ui.NotificationUi
import dagger.Binds
import dagger.Module

@Module
interface UiModule {

    @Binds
    fun bindsProgressUi(mainActivity: MainActivity): ProgressUi

    @Binds
    fun bindsShowMessage(mainActivity: MainActivity): NotificationUi

}
