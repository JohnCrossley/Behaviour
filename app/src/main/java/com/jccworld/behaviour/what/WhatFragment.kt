package com.jccworld.behaviour.what

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.GridLayoutManager
import com.jccworld.behaviour.R
import com.jccworld.behaviour.di.InjectableModelViewFactory
import com.jccworld.behaviour.ui.*
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_what_chooser.*
import kotlinx.android.synthetic.main.fragment_what_chooser.next
import java.lang.IllegalArgumentException
import javax.inject.Inject

class WhatFragment : DaggerFragment() {

    @Inject
    lateinit var injectableModelViewFactory: InjectableModelViewFactory

    @Inject
    lateinit var progressUi: ProgressUi

    @Inject
    lateinit var notificationUi: NotificationUi

    @Inject
    lateinit var navigation: Navigation

    private lateinit var model: WhatViewModel

    private lateinit var adapter: BehaviourAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_what_chooser, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(this, injectableModelViewFactory).get(WhatViewModel::class.java)

        behavioursRecyclerView.layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.behaviourColumns))
        adapter = BehaviourAdapter(context, true)
        behavioursRecyclerView.adapter = adapter

        val tracker = SelectionTracker.Builder<Long>(
                "selectedBehaviours",
                behavioursRecyclerView,
                StableIdKeyProvider(behavioursRecyclerView),
                BehaviourViewHolderItemDetailsLookup(behavioursRecyclerView),
                StorageStrategy.createLongStorage()
        )
                .withSelectionPredicate(SelectionPredicates.createSelectSingleAnything())
                .build()

        adapter.tracker = tracker

        tracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                if (!tracker.selection.isEmpty)
                    model.selected.value = tracker.selection.first()
            }
        })

        model.state.observe(viewLifecycleOwner, Observer {
            progressUi.hideProgress()

            when (it) {
                State.LOADING -> progressUi.showProgress()
                State.READY -> {
                }
                State.FAIL -> TODO()
                State.NO_BEHAVIOURS_IN_DB -> AlertDialog.Builder(context!!).createNoBehaviourDialog().show()
                null -> throw IllegalArgumentException("State was null")
            }
        })

        model.valid.observe(viewLifecycleOwner, Observer {
            next.isEnabled = it
        })

        model.behaviours.observe(viewLifecycleOwner, Observer { behaviourList -> adapter.update(behaviourList) })

        next.setOnClickListener {
            model.save { navigation.toSummary() }
        }
    }

    private fun AlertDialog.Builder.createNoBehaviourDialog() = this
            .setTitle(getString(R.string.no_behaviours_dialog_title))
            .setView(R.layout.inc_no_behaviours_dialog)
            .setCancelable(false)
            .setPositiveButton(R.string.no_behaviours_dialog_add_defaults) { _, _ -> model.addDefaultBehaviours() }
            .setNegativeButton(R.string.no_behaviours_dialog_customise) { _, _ -> TODO("Not built") }

    companion object {
        const val TAG = "WhatFragment"

        @JvmStatic
        fun newInstance() = WhatFragment()
    }
}