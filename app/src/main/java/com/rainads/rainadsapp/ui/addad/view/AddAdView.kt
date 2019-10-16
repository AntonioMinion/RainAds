package com.rainads.rainadsapp.ui.addad.view

import com.rainads.rainadsapp.data.network.models.PointsResponse
import com.rainads.rainadsapp.ui.base.view.MVPView
import com.rainads.rainadsapp.util.ToastType

interface AddAdView : MVPView {
    fun showMessage(type: ToastType, message: String)
    fun loadOptions(options: List<PointsResponse>)
}