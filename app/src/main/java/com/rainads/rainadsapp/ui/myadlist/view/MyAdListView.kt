package com.rainads.rainadsapp.ui.myadlist.view

import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.ui.base.view.MVPView
import com.rainads.rainadsapp.util.ToastType

interface MyAdListView : MVPView {
    fun userDownloaded(user: User)
    fun showMessage(type: ToastType, message: String?)
}