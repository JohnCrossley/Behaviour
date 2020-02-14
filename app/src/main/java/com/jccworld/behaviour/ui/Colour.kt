package com.jccworld.behaviour.ui

import android.content.Context
import androidx.core.content.ContextCompat
import com.jccworld.behaviour.R

fun Context.getSelectedColour() = ContextCompat.getColor(this, R.color.colorAccent)

fun Context.getUnselectedColour() = ContextCompat.getColor(this, R.color.white)