package com.rainads.rainadsapp.ui.addad.interactor

import com.rainads.rainadsapp.ui.base.interactor.MVPInteractor
import io.reactivex.Observable


interface IAddAdInteractor : MVPInteractor {

    fun createAdCall(
        adUrl: String
        , adPrice: String
        , adDuration: String
        , adDescription: String
        , adCountries: List<String>
    )
            : Observable<String>

}