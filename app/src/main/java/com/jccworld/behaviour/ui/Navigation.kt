package com.jccworld.behaviour.ui

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.transaction
import com.jccworld.behaviour.R
import com.jccworld.behaviour.addchild.AddChildFragment
import com.jccworld.behaviour.dashboard.DashboardFragment
import com.jccworld.behaviour.debug.DebugFragment
import com.jccworld.behaviour.managechildren.ManageChildrenFragment
import com.jccworld.behaviour.summary.SummaryFragment
import com.jccworld.behaviour.what.WhatFragment
import com.jccworld.behaviour.who.WhoFragment

class Navigation(private val fragmentManager: FragmentManager) {

    fun onBoard() {
        toDashboard()

        toDebug()
        /*
        toManageChildren()
        toAddChild()
        */
    }

    fun toDashboard() {
        if (fragmentManager.findFragmentByTag(ManageChildrenFragment.TAG) != null) {
            fragmentManager.popBackStack(ManageChildrenFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } else {
            fragmentManager.commit {
                replace(R.id.content, DashboardFragment.newInstance(), DashboardFragment.TAG)
                addToBackStack(DashboardFragment.TAG)
            }
        }
    }

    fun toManageChildren() {
        if (fragmentManager.findFragmentByTag(AddChildFragment.TAG) != null) {
            fragmentManager.popBackStack(AddChildFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } else {
            fragmentManager.commit {
                replace(R.id.content, ManageChildrenFragment.newInstance(), ManageChildrenFragment.TAG)
                addToBackStack(ManageChildrenFragment.TAG)
            }
        }
    }

    fun toAddChild() {
        fragmentManager.commit {
            replace(R.id.content, AddChildFragment.newInstance(), AddChildFragment.TAG)
            addToBackStack(AddChildFragment.TAG)
        }
    }

    fun toWho() {
        fragmentManager.commit {
            replace(R.id.content, WhoFragment.newInstance(), WhoFragment.TAG)
            addToBackStack(WhoFragment.TAG)
        }
    }

    fun toWhat() {
        fragmentManager.commit {
            replace(R.id.content, WhatFragment.newInstance(), WhatFragment.TAG)
            addToBackStack(WhatFragment.TAG)
        }
    }

    fun toSummary() {
        fragmentManager.popBackStack(DashboardFragment.TAG, POP_BACK_STACK_UP_TO_BUT_NOT_INCLUDING)
        fragmentManager.commit {
            replace(R.id.content, SummaryFragment.newInstance(), SummaryFragment.TAG)
            addToBackStack(SummaryFragment.TAG)
        }
    }

    fun toDebug() {
        fragmentManager.commit {
            replace(R.id.content, DebugFragment.newInstance(), DebugFragment.TAG)
            addToBackStack(DebugFragment.TAG)
        }
    }

    companion object {
        const val POP_BACK_STACK_UP_TO_BUT_NOT_INCLUDING: Int = 0
    }

}
