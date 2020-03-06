package com.rainads.rainadsapp.ui.transferpoints.view

import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.ui.base.view.MVPView
import com.rainads.rainadsapp.util.ToastType

interface TransferPointsView: MVPView {
    fun sendFundsCallback(toastType: ToastType, message: String)
    fun userDownloaded(user: User)
}