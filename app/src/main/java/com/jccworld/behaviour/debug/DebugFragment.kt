package com.jccworld.behaviour.debug

import android.annotation.SuppressLint
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
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_debug.*
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

    private val subscriptions = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_debug, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(this, injectableModelViewFactory).get(DebugViewModel::class.java)

        addDebugData.setOnClickListener { model.addDebugData() }
        logChildren.setOnClickListener { model.logChildren() }
        logBehaviours.setOnClickListener { model.logBehaviours() }
        logRecords.setOnClickListener { model.logRecords() }
        clearLog.setOnClickListener { log.setText("") }

        subscriptions.add(model.log.subscribe(
                { event -> log.setText("${log.text}\n$event") },
                { e -> log.setText("${log.text}\nUnexpected: $e") }
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()

        subscriptions.clear()
    }

    companion object {
        const val TAG = "DebugFragment"

        @JvmStatic
        fun newInstance() = DebugFragment()
    }

}