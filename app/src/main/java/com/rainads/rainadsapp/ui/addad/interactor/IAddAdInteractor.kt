package com.rainads.rainadsapp.ui.addad.interactor

import com.rainads.rainadsapp.data.network.models.SatoshiResponse
import com.rainads.rainadsapp.ui.base.interactor.MVPInteractor
import io.reactivex.Observable


interface IAddAdInteractor : MVPInteractor {

    fun getOptionsList(): Observable<List<SatoshiResponse>>

    fun createAdCall(
        adUrl: String
        , adPrice: String
        , adDuration: String
        , adDescription: String
        , adCountries: List<String>
    )
            : Observable<String>

}