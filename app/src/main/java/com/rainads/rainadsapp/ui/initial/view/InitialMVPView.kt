package com.rainads.rainadsapp.ui.initial.view

import com.rainads.rainadsapp.data.network.models.Country
import com.rainads.rainadsapp.ui.base.view.MVPView
import com.rainads.rainadsapp.util.ToastType

interface InitialMVPView : MVPView {
    fun showMessage(type: ToastType, message: String, token: String)
    fun showErrorMessage(errorCode: Int)
    fun loadCountries(countries: List<Country>)
    fun openMainActivity()
}