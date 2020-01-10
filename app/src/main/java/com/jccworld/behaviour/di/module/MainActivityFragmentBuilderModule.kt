package com.jccworld.behaviour.di.module

import com.jccworld.behaviour.addchild.AddChildFragment
import com.jccworld.behaviour.dashboard.DashboardFragment
import com.jccworld.behaviour.debug.DebugFragment
import com.jccworld.behaviour.managechildren.ManageChildrenFragment
import com.jccworld.behaviour.summary.SummaryFragment
import com.jccworld.behaviour.what.WhatFragment
import com.jccworld.behaviour.who.WhoFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityFragmentBuilderModule {

    @ContributesAndroidInjector
    internal abstract fun dashboardFragment(): DashboardFragment

    @ContributesAndroidInjector
    internal abstract fun manageChildrenFragment(): ManageChildrenFragment

    @ContributesAndroidInjector
    internal abstract fun addChildFragment(): AddChildFragment

    @ContributesAndroidInjector
    internal abstract fun whoFragment(): WhoFragment

    @ContributesAndroidInjector
    internal abstract fun whatFragment(): WhatFragment

    @ContributesAndroidInjector
    internal abstract fun summaryFragment(): SummaryFragment

    @ContributesAndroidInjector
    internal abstract fun debugFragment(): DebugFragment

}
