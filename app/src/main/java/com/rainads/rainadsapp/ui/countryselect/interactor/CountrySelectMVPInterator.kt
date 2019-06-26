package com.rainads.rainadsapp.ui.countryselect.interactor

import com.rainads.rainadsapp.data.network.models.Country
import com.rainads.rainadsapp.ui.base.interactor.MVPInteractor
import io.reactivex.Observable

interface CountrySelectMVPInterator : MVPInteractor {

    fun getCountries(): Observable<List<Country>>

}