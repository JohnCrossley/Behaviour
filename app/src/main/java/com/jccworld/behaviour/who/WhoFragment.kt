package com.jccworld.behaviour.who

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.jccworld.behaviour.R
import com.jccworld.behaviour.di.InjectableModelViewFactory
import com.jccworld.behaviour.domain.Child
import com.jccworld.behaviour.domain.Dichotomy
import com.jccworld.behaviour.ui.*
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_who_chooser.*
import javax.inject.Inject

class WhoFragment : DaggerFragment() {

    @Inject
    lateinit var injectableModelViewFactory: InjectableModelViewFactory

    @Inject
    lateinit var progressUi: ProgressUi

    @Inject
    lateinit var notificationUi: NotificationUi

    @Inject
    lateinit var navigation: Navigation

    private lateinit var model: WhoViewModel

    private lateinit var adapter: ChildrenAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_who_chooser, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        @ColorInt
        val selectedColour = context!!.getSelectedColour()

        @ColorInt
        val unselectedColour = context!!.getUnselectedColour()

        model = ViewModelProvider(this, injectableModelViewFactory).get(WhoViewModel::class.java)

        adapter = ChildrenAdapter(context, true, selectedColour, unselectedColour)
        childrenRecyclerView.adapter = adapter

        val tracker = SelectionTracker.Builder<Long>(
                "selectedChildren",
                childrenRecyclerView,
                StableIdKeyProvider(childrenRecyclerView),
                ChildViewHolderItemDetailsLookup(childrenRecyclerView),
                StorageStrategy.createLongStorage()
            )
            .withSelectionPredicate(SelectionPredicates.createSelectAnything())
            .build()

        adapter.tracker = tracker

        tracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    val idList = tracker.selection.toList()
                    val list = model.children.value?.filter { child ->  idList.contains(child.id) } ?: throw IllegalArgumentException("Children isn't set")
                    model.selected.value = list.toList()
                }
            })

        model.state.observe(viewLifecycleOwner, Observer {
            progressUi.hideProgress()

            when(it) {
                State.LOADING -> progressUi.showProgress()
                State.READY -> progressUi.hideProgress()
                State.FAIL -> notificationUi.showMessage(R.string.load_children_failed)
                null -> throw IllegalArgumentException("State was null")
            }
        })

        model.good.observe(viewLifecycleOwner, Observer {
            good.isSelected = it
            good.setColorFilter(if (it) selectedColour else unselectedColour)
        })

        model.bad.observe(viewLifecycleOwner, Observer {
            bad.isSelected = it
            bad.setColorFilter(if (it) selectedColour else unselectedColour)
        })

        model.children.observe(viewLifecycleOwner, Observer<List<Child>> { childrenList -> adapter.update(childrenList) })

        model.valid.observe(viewLifecycleOwner, Observer {
            next.isEnabled = it
        })

        next.setOnClickListener {
            model.save{
                navigation.toWhat()
            }
        }

        good.setOnClickListener { model.dichotomy.value = Dichotomy.GOOD }
        bad.setOnClickListener { model.dichotomy.value = Dichotomy.BAD }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        adapter.tracker?.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        adapter.tracker?.onRestoreInstanceState(savedInstanceState)
    }

    companion object {
        const val TAG = "WhoFragment"

        @JvmStatic
        fun newInstance() = WhoFragment()
    }
}
