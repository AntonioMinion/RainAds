package com.rainads.rainadsapp.ui.deposit.view

import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.ui.base.view.MVPView
import com.rainads.rainadsapp.util.ToastType

interface DepositView : MVPView {
    fun showMessage(type: ToastType, message: String)
    fun userDownloaded(user: User)
}