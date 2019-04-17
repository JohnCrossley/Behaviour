package com.jccword.behaviour.di.module

import com.jccword.behaviour.addchild.AddChildFragment
import com.jccword.behaviour.dashboard.DashboardFragment
import com.jccword.behaviour.managechildren.ManageChildrenFragment
import com.jccword.behaviour.summary.SummaryFragment
import com.jccword.behaviour.what.WhatFragment
import com.jccword.behaviour.who.WhoFragment

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

}
