package com.jccword.behaviour.ui

import androidx.annotation.StringRes

/**
 * Shows a message in the containing Activity as a Snackbar
 */
interface NotificationUi {

    fun showMessage(message: String)

    fun showMessage(@StringRes resId: Int)

}
