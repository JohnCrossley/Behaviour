package com.jccword.behaviour.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recording(val dichotomy: Dichotomy, val childrenIds: List<Long>, var behaviourId: Long = 0) : Parcelable {
    fun setBehaviour(selected: Long) {
        this.behaviourId = selected
    }
}
