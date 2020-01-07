package com.jccworld.behaviour.dashboard

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jccworld.behaviour.ui.Navigation
import com.jccworld.behaviour.R
import com.jccworld.behaviour.di.InjectableModelViewFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DashboardFragment : DaggerFragment() {

    @Inject
    lateinit var navigation: Navigation

    @Inject
    lateinit var injectableModelViewFactory: InjectableModelViewFactory

    private lateinit var model: DashboardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        model = ViewModelProvider(this, injectableModelViewFactory).get(DashboardViewModel::class.java)

        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { navigation.toWho() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return if (id == R.id.manageChildren) {
            navigation.toManageChildren()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    companion object {
        val TAG = "DashboardFragment"

        @JvmStatic
        fun newInstance() = DashboardFragment()
    }

}