package com.jccword.behaviour.ui

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.transaction
import com.jccword.behaviour.R
import com.jccword.behaviour.addchild.AddChildFragment
import com.jccword.behaviour.dashboard.DashboardFragment
import com.jccword.behaviour.domain.Recording
import com.jccword.behaviour.managechildren.ManageChildrenFragment
import com.jccword.behaviour.summary.SummaryFragment
import com.jccword.behaviour.what.WhatFragment
import com.jccword.behaviour.who.WhoFragment

class Navigation(private val fragmentManager: FragmentManager) {

    fun onBoard() {
        toDashboard()
        toManageChildren()
        toAddChild()
    }

    fun toDashboard() {
        if (fragmentManager.findFragmentByTag(ManageChildrenFragment.TAG) != null) {
            fragmentManager.popBackStack(ManageChildrenFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } else {
            fragmentManager.transaction {
                replace(R.id.content, DashboardFragment.newInstance(), DashboardFragment.TAG)
                addToBackStack(DashboardFragment.TAG)
            }
        }
    }

    fun toManageChildren() {
        if (fragmentManager.findFragmentByTag(AddChildFragment.TAG) != null) {
            fragmentManager.popBackStack(AddChildFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } else {
            fragmentManager.transaction {
                replace(R.id.content, ManageChildrenFragment.newInstance(), ManageChildrenFragment.TAG)
                addToBackStack(ManageChildrenFragment.TAG)
            }
        }
    }

    fun toAddChild() {
        fragmentManager.transaction {
            replace(R.id.content, AddChildFragment.newInstance(), AddChildFragment.TAG)
            addToBackStack(AddChildFragment.TAG)
        }
    }

    fun toWho() {
        fragmentManager.transaction {
            replace(R.id.content, WhoFragment.newInstance(), WhoFragment.TAG)
            addToBackStack(WhoFragment.TAG)
        }
    }

    fun toWhat() {
        fragmentManager.transaction {
            replace(R.id.content, WhatFragment.newInstance(), WhatFragment.TAG)
            addToBackStack(WhatFragment.TAG)
        }
    }

    fun toSummary() {
        fragmentManager.popBackStack(DashboardFragment.TAG, POP_BACK_STACK_UP_TO_BUT_NOT_INCLUDING)
        fragmentManager.transaction {
            replace(R.id.content, SummaryFragment.newInstance(), SummaryFragment.TAG)
            addToBackStack(SummaryFragment.TAG)
        }
    }

    companion object {
        const val POP_BACK_STACK_UP_TO_BUT_NOT_INCLUDING: Int = 0
    }

}
