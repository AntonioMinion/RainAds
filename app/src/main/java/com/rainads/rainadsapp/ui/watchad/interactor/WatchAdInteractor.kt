package com.rainads.rainadsapp.ui.watchad.interactor

import android.content.Context
import com.rainads.rainadsapp.data.ApiCalls
import com.rainads.rainadsapp.data.network.models.WatchAdRequest
import com.rainads.rainadsapp.data.preferences.PreferenceHelper
import com.rainads.rainadsapp.ui.base.interactor.BaseInteractor
import javax.inject.Inject

class WatchAdInteractor @Inject internal constructor(
    private val mContext: Context,
    preferenceHelper: PreferenceHelper,
    apiCalls: ApiCalls
) :
    BaseInteractor(preferenceHelper, apiCalls),
    IWatchAdInteractor {

    override fun watchAd(adId: String) = apiCalls.watchAd(WatchAdRequest(adId))

}
