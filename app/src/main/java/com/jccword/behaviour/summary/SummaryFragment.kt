package com.jccword.behaviour.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jccword.behaviour.R
import com.jccword.behaviour.di.InjectableModelViewFactory
import com.jccword.behaviour.domain.Recording
import com.jccword.behaviour.ui.Navigation
import com.jccword.behaviour.ui.NotificationUi
import com.jccword.behaviour.ui.ProgressUi
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_summary.*
import javax.inject.Inject

class SummaryFragment : DaggerFragment() {

    @Inject
    lateinit var injectableModelViewFactory: InjectableModelViewFactory

    @Inject
    lateinit var progressUi: ProgressUi

    @Inject
    lateinit var notificationUi: NotificationUi

    @Inject
    lateinit var navigation: Navigation

    private lateinit var model: SummaryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProviders.of(this, injectableModelViewFactory).get(SummaryViewModel::class.java)

        dashboard.setOnClickListener{ navigation.toDashboard() }

        model.state.observe(this, Observer {
            progressUi.hideProgress()
            dashboard.visibility = View.GONE

            when(it) {
                State.SAVING -> progressUi.showProgress()
                State.SAVED -> {
                    notificationUi.showMessage(R.string.save_record_success)
                    dashboard.visibility = View.VISIBLE
                }
                State.FAIL -> notificationUi.showMessage(R.string.save_record_failed)
                null -> IllegalArgumentException("State is null")
            }
        })

        arguments?.get(RECORDING).let {
            if (it is Recording) {
                model.save(it)
                arguments?.remove(RECORDING)
            }
        }
    }

    companion object {
        const val TAG = "SummaryFragment"

        const val RECORDING = "Recording"

        @JvmStatic
        fun newInstance(recording: Recording): SummaryFragment {
            val frag = SummaryFragment()
            val bundle = Bundle()
            bundle.putParcelable(RECORDING, recording)
            frag.arguments = bundle

            return frag
        }
    }

}