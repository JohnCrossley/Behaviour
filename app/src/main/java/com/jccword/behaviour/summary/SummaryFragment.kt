package com.jccword.behaviour.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jccword.behaviour.R
import com.jccword.behaviour.database.entity.ChildBehaviourRecordEntity
import com.jccword.behaviour.di.InjectableModelViewFactory
import com.jccword.behaviour.ext.startOfWeek
import com.jccword.behaviour.ui.Navigation
import com.jccword.behaviour.ui.NotificationUi
import com.jccword.behaviour.ui.ProgressUi
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_summary.*
import org.threeten.bp.LocalDate
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
                State.FETCHED_SUMMARY -> {
                    dashboard.visibility = View.VISIBLE
                    dump(model.summary.value!!)
                }
                State.FAIL -> notificationUi.showMessage(R.string.save_record_failed)
                null -> IllegalArgumentException("State is null")
            }
        })

        model.save()
    }

    private fun dump(summary: Map<Long, List<ChildBehaviourRecordEntity>>) {
        for(id in summary) {
            println("[JCC] SummaryFragment.dump - CHILD ID: ${id.key}")
            
            for(record in id.value) {
                println("[JCC] SummaryFragment.dump ----> RECORD id ${record.id}")
            }
        }



        LocalDate.now().startOfWeek()
    }

    companion object {
        const val TAG = "SummaryFragment"

        @JvmStatic
        fun newInstance(): SummaryFragment = SummaryFragment()
    }

}