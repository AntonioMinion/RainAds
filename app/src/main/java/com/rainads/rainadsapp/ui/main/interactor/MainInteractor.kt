package com.rainads.rainadsapp.ui.main.interactor

import android.content.Context
import com.rainads.rainadsapp.data.ApiCalls
import com.rainads.rainadsapp.data.preferences.PreferenceHelper
import com.rainads.rainadsapp.ui.base.interactor.BaseInteractor
import javax.inject.Inject

class MainInteractor @Inject internal constructor(
    private val mContext: Context,
    preferenceHelper: PreferenceHelper,
    apiCalls: ApiCalls
) :
    BaseInteractor(preferenceHelper, apiCalls),
    MainMVPInteractor {

    override fun downloadAd() = apiCalls.getAd()

    override fun downloadUser() = apiCalls.getUser(preferenceHelper.getAccessToken()!!)

}