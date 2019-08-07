package com.rainads.rainadsapp.ui.base.view

interface MVPView {

    fun showProgress()

    fun hideProgress()

    fun showErrorMessage(error: Throwable)

}