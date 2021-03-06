package com.jccworld.behaviour.ui

/**
 * Shows UI progress in the Toolbar
 */
interface ProgressUi {

    fun showProgress(show: Boolean = true)

    fun hideProgress()

}
