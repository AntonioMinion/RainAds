package com.rainads.rainadsapp.ui.initial.view

import com.rainads.rainadsapp.data.network.models.Country
import com.rainads.rainadsapp.ui.base.view.MVPView

interface InitialMVPView : MVPView {
    fun showValidationMessage(errorCode: Int)
    fun loadCountries(countries: List<Country>)
    fun openMainActivity()
}