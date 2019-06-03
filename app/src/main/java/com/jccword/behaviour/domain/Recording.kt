package com.jccword.behaviour.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recording(
        val dichotomy: Dichotomy,
        val childrenIds: List<Long>,
        var stars: Short = 0,
        var behaviourId: Long = 0) : Parcelable {
}
