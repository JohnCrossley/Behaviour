package com.jccworld.behaviour

import com.jakewharton.threetenabp.AndroidThreeTen
import com.jccworld.behaviour.di.component.ApplicationComponent
import com.jccworld.behaviour.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class Application : DaggerApplication() {

    var component: ApplicationComponent = DaggerApplicationComponent.builder().app(this).build()

    init {
        component.inject(this)
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        System.out.println("[JCC] - APP START ***********************************************************************")
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return component
    }

    fun component(): ApplicationComponent = component

}
