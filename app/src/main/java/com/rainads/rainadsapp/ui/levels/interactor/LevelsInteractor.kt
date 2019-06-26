package com.rainads.rainadsapp.ui.levels.interactor

import android.content.Context
import com.rainads.rainadsapp.data.ApiCalls
import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.data.preferences.PreferenceHelper
import com.rainads.rainadsapp.ui.base.interactor.BaseInteractor
import io.reactivex.Observable
import javax.inject.Inject

class LevelsInteractor @Inject internal constructor(
    private val mContext: Context,
    preferenceHelper: PreferenceHelper,
    apiCalls: ApiCalls
) :
    BaseInteractor(preferenceHelper, apiCalls),
    ILevelsInteractor {
    override fun getReferralList(): Observable<User> = apiCalls.getUser(preferenceHelper.getAccessToken()!!)

}