package com.jccword.behaviour.di.component

import com.jccword.behaviour.Application
import com.jccword.behaviour.MainActivity
import com.jccword.behaviour.di.InjectableModelViewFactory
import com.jccword.behaviour.di.module.NavigationModule
import com.jccword.behaviour.di.module.UiModule
import com.jccword.behaviour.di.scope.PerActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector

@Component(dependencies = [ ApplicationComponent::class ], modules = [ NavigationModule::class, UiModule::class ])
@PerActivity
interface InjectableModelViewFactoryComponent : AndroidInjector<InjectableModelViewFactory> {

    @Component.Builder
    interface Builder {

        fun appComponent(applicationComponent: ApplicationComponent): Builder

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun mainActivity(mainActivity: MainActivity): Builder

        fun build(): InjectableModelViewFactoryComponent

    }
}
