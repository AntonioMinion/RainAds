package com.rainads.rainadsapp.ui.myadlist.view

import com.rainads.rainadsapp.data.network.models.AdModel
import com.rainads.rainadsapp.ui.base.view.MVPView
import com.rainads.rainadsapp.util.ToastType

interface MyAdListView : MVPView {
    fun displayMyAdsList(adList: List<AdModel>?)
    fun showMessage(type: ToastType, message: String?)
}