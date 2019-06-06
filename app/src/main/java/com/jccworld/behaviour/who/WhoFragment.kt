package com.jccworld.behaviour.who

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.GridLayoutManager
import com.jccworld.behaviour.R
import com.jccworld.behaviour.di.InjectableModelViewFactory
import com.jccworld.behaviour.domain.Child
import com.jccworld.behaviour.domain.Dichotomy
import com.jccworld.behaviour.ui.*
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_who_chooser.*
import java.lang.IllegalArgumentException
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

        model = ViewModelProviders.of(this, injectableModelViewFactory).get(WhoViewModel::class.java)

        childrenRecyclerView.layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.childrenColumns))
        adapter = ChildrenAdapter(context, true)
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
                    //rx overkill version
                    /*val toList = Observable.just(tracker.selection.toList())
                            .flatMapIterable { childIds ->
                                val children = mutableListOf<Child>()
                                children.add(model.children.value?.find { childIds.contains(it.id) }
                                        ?: throw IllegalArgumentException("ID belongs to a Child that doesn't exist"))
                                children
                        }.toList().blockingGet()

                    model.selected.value = toList.toList()*/

                    //Kotlin std lib - non-rx
                    val idList = tracker.selection.toList()
                    val list = model.children.value?.filter { child ->  idList.contains(child.id) } ?: throw IllegalArgumentException("Children isn't set")
                    model.selected.value = list.toList()
                }
            })

        model.state.observe(this, Observer {
            progressUi.hideProgress()

            when(it) {
                State.LOADING -> progressUi.showProgress()
                State.READY -> progressUi.hideProgress()
                State.FAIL -> notificationUi.showMessage(R.string.load_children_failed)
                null -> IllegalArgumentException("State was null")
            }
        })

        model.good.observe(this, Observer {
            good.isSelected = it
            goodSelected.visibility = if (it) View.VISIBLE else View.GONE
        })

        model.bad.observe(this, Observer {
            bad.isSelected = it
            badSelected.visibility = if (it) View.VISIBLE else View.GONE
        })

        model.children.observe(this, Observer<List<Child>> { childrenList -> adapter.update(childrenList) })

        model.valid.observe(this, Observer {
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
        val TAG = "WhoFragment"

        @JvmStatic
        fun newInstance() = WhoFragment()
    }
}
