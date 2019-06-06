package com.jccworld.behaviour.di.component

import com.jccworld.behaviour.Application
import com.jccworld.behaviour.MainActivity
import com.jccworld.behaviour.di.InjectableModelViewFactory
import com.jccworld.behaviour.di.module.NavigationModule
import com.jccworld.behaviour.di.module.UiModule
import com.jccworld.behaviour.di.scope.PerActivity
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
