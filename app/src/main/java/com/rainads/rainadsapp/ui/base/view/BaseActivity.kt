package com.rainads.rainadsapp.ui.base.view

import android.app.Dialog
import android.os.Bundle
import com.rainads.rainadsapp.util.AppUtils
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity(), MVPView, BaseFragment.CallBack {

    private var progressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        performDI()
        super.onCreate(savedInstanceState)
    }

    override fun hideProgress() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }

    override fun showProgress() {
        hideProgress()
        progressDialog = AppUtils.showLoadingDialog(this)
    }

    private fun performDI() = AndroidInjection.inject(this)

}