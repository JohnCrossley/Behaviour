package com.jccworld.behaviour.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jccworld.behaviour.BootViewModel
import com.jccworld.behaviour.addchild.AddChildViewModel
import com.jccworld.behaviour.dashboard.DashboardViewModel
import com.jccworld.behaviour.debug.DebugViewModel
import com.jccworld.behaviour.di.InjectableModelViewFactory
import com.jccworld.behaviour.di.ViewModelKey
import com.jccworld.behaviour.managechildren.ManageChildrenViewModel
import com.jccworld.behaviour.summary.SummaryViewModel
import com.jccworld.behaviour.what.WhatViewModel
import com.jccworld.behaviour.who.WhoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: InjectableModelViewFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(BootViewModel::class)
    abstract fun bindBootViewModel(viewModel: BootViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindDashboardViewModel(viewModel: DashboardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ManageChildrenViewModel::class)
    abstract fun bindManageChildrenViewModel(viewModel: ManageChildrenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddChildViewModel::class)
    abstract fun bindAddChildViewModel(viewModel: AddChildViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WhoViewModel::class)
    abstract fun bindWhoViewModel(viewModel: WhoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WhatViewModel::class)
    abstract fun bindWhatViewModel(viewModel: WhatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SummaryViewModel::class)
    abstract fun bindSummaryViewModel(viewModel: SummaryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DebugViewModel::class)
    abstract fun bindDebugViewModel(viewModel: DebugViewModel): ViewModel

}