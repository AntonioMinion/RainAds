package com.rainads.rainadsapp.ui.countryselect.view

import com.rainads.rainadsapp.data.network.models.Country
import com.rainads.rainadsapp.ui.base.view.MVPView


interface CountrySelectDialogMVPView : MVPView {
    fun loadCountries(countries: List<Country>)
    fun dismissDialog()
}