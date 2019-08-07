package com.rainads.rainadsapp.ui.addad.interactor

import android.content.Context
import com.rainads.rainadsapp.data.ApiCalls
import com.rainads.rainadsapp.data.network.models.NewAdRequest
import com.rainads.rainadsapp.data.network.models.SatoshiResponse
import com.rainads.rainadsapp.data.preferences.PreferenceHelper
import com.rainads.rainadsapp.ui.base.interactor.BaseInteractor
import io.reactivex.Observable
import javax.inject.Inject

class AddAdInteractor @Inject internal constructor(
        private val mContext: Context,
        preferenceHelper: PreferenceHelper,
        apiCalls: ApiCalls
) :
        BaseInteractor(preferenceHelper, apiCalls),
        IAddAdInteractor {

    override fun getOptionsList(): Observable<List<SatoshiResponse>> =
            apiCalls.getSatoshiList()

    override fun createAdCall(
            adUrl: String,
            adPrice: String,
            adDuration: String,
            adDescription: String,
            adCountries: List<String>
    ) = apiCalls.createAd(
            NewAdRequest(
                    adUrl,
                    adDescription,
                    adPrice,
                    adDuration,
                    adCountries
            )
    )

}