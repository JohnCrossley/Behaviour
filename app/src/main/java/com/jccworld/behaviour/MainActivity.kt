package com.jccworld.behaviour

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.jccworld.behaviour.di.InjectableModelViewFactory
import com.jccworld.behaviour.ui.Navigation
import com.jccworld.behaviour.ui.ProgressUi
import com.jccworld.behaviour.ui.NotificationUi
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException
import javax.inject.Inject

class MainActivity : ProgressUi, NotificationUi, DaggerAppCompatActivity() {

    @Inject
    lateinit var navigation: Navigation

    @Inject
    lateinit var progressUi: ProgressUi

    @Inject
    lateinit var injectableModelViewFactory: InjectableModelViewFactory


    private lateinit var model: BootViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        model = ViewModelProviders.of(this, injectableModelViewFactory).get(BootViewModel::class.java)

        supportFragmentManager.addOnBackStackChangedListener {
            println("[JCC] back stack changed: new size is ${supportFragmentManager.backStackEntryCount}")

            for(i in 0 until supportFragmentManager.backStackEntryCount)
                println("[JCC] Backstack @$i = ${supportFragmentManager.getBackStackEntryAt(i)}")
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            model.state.observe(this, Observer {
                progressUi.showProgress(false)

                when (it) {
                    State.BOOTING -> progressUi.showProgress()
                    State.NO_CHILDREN_IN_DB -> navigation.onBoard()
                    State.CHILDREN_IN_DB -> navigation.toDashboard()
                    State.FAILED_TO_DETERMINE -> navigation.toDashboard()
                    null -> IllegalArgumentException("State was null")

                }
            })
        }
    }

    override fun showProgress(show: Boolean) {
        runOnUiThread { progressBar.visibility = if (show) View.VISIBLE else View.GONE }
    }

    override fun hideProgress() {
        runOnUiThread { progressBar.visibility = View.GONE }
    }

    override fun showMessage(message: String) {
        runOnUiThread { Snackbar.make(mainActivityRootView, message, Snackbar.LENGTH_SHORT).show() }
    }

    override fun showMessage(resId: Int) {
        runOnUiThread { Snackbar.make(mainActivityRootView, resId, Snackbar.LENGTH_SHORT).show() }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1)
            finish()
        else
            super.onBackPressed()
    }
}
