package com.jccworld.behaviour.managechildren

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.jccworld.behaviour.ui.Navigation
import com.jccworld.behaviour.R
import com.jccworld.behaviour.di.InjectableModelViewFactory
import com.jccworld.behaviour.domain.Child
import com.jccworld.behaviour.ui.ChildrenAdapter
import com.jccworld.behaviour.ui.NotificationUi
import com.jccworld.behaviour.ui.ProgressUi
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_manage_children.*
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ManageChildrenFragment : DaggerFragment() {
    private lateinit var model: ManageChildrenViewModel
    private lateinit var adapter: ChildrenAdapter

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
        return inflater.inflate(R.layout.fragment_manage_children, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProviders.of(this, injectableModelViewFactory).get(ManageChildrenViewModel::class.java)

        childrenRecyclerView.layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.childrenColumns))
        adapter = ChildrenAdapter(context, false)
        childrenRecyclerView.adapter = adapter

        dashboard.setOnClickListener { navigation.toDashboard() }

        fab.setOnClickListener {
            navigation.toAddChild()
        }

        model.state.observe(this, Observer {
            when(it) {
                State.LOADING -> progressUi.showProgress()
                State.READY -> progressUi.hideProgress()
                State.FAIL -> notificationUi.showMessage(R.string.load_children_failed)
                null -> IllegalArgumentException("State was null")
            }
        })

        model.children.observe(this, Observer<List<Child>> { childList ->
            adapter.update(childList)
        })
    }

    companion object {
        val TAG = "ManageChildrenFragment"

        @JvmStatic
        fun newInstance() = ManageChildrenFragment()
    }

}