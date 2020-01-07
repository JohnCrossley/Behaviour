package com.jccworld.behaviour.addchild

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jccworld.behaviour.R
import com.jccworld.behaviour.databinding.FragmentAddChildBinding
import com.jccworld.behaviour.di.InjectableModelViewFactory
import com.jccworld.behaviour.ui.Navigation
import com.jccworld.behaviour.ui.NotificationUi
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add_child.*
import java.lang.IllegalArgumentException
import javax.inject.Inject

class AddChildFragment : DaggerFragment() {
    private lateinit var model: AddChildViewModel

    @Inject
    lateinit var navigation: Navigation

    @Inject
    lateinit var notificationUi: NotificationUi

    @Inject
    lateinit var injectableViewModelFactory: InjectableModelViewFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddChildBinding.bind(view)

        model = ViewModelProvider(this, injectableViewModelFactory).get(AddChildViewModel::class.java)
        binding.child = model

        model.valid.observe(this, Observer {
            next.isEnabled = it
        })

        model.state.observe(this, Observer {
            when (it) {
                State.READY -> {
                    //NO-OP
                }
                State.SUCCESS -> {
                    notificationUi.showMessage(R.string.add_child_success)
                    navigation.toManageChildren()
                }
                State.FAIL -> {
                    notificationUi.showMessage(R.string.add_child_failed)
                }
                null -> IllegalArgumentException("State was null")
            }
        })

        next.setOnClickListener { model.submit() }
    }

    companion object {
        const val TAG = "AddChildFragment"

        @JvmStatic
        fun newInstance() = AddChildFragment()
    }

}