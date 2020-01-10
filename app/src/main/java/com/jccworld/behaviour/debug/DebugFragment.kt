package com.jccworld.behaviour.debug

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.jccworld.behaviour.R
import com.jccworld.behaviour.di.InjectableModelViewFactory
import com.jccworld.behaviour.ui.Navigation
import com.jccworld.behaviour.ui.NotificationUi
import com.jccworld.behaviour.ui.ProgressUi
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DebugFragment : DaggerFragment() {
    private lateinit var model: DebugViewModel

    @Inject
    lateinit var notificationUi: NotificationUi

    @Inject
    lateinit var progressUi: ProgressUi

    @Inject
    lateinit var navigation: Navigation

    @Inject
    lateinit var injectableModelViewFactory: InjectableModelViewFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_debug, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(this, injectableModelViewFactory).get(DebugViewModel::class.java)
    }

    companion object {
        const val TAG = "DebugFragment"

        @JvmStatic
        fun newInstance() = DebugFragment()
    }

}