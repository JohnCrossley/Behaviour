package com.jccword.behaviour.di.module

import com.jccword.behaviour.MainActivity
import com.jccword.behaviour.ui.ProgressUi
import com.jccword.behaviour.ui.NotificationUi
import dagger.Binds
import dagger.Module

@Module
interface UiModule {

    @Binds
    fun bindsProgressUi(mainActivity: MainActivity): ProgressUi

    @Binds
    fun bindsShowMessage(mainActivity: MainActivity): NotificationUi

}
