package com.rainads.rainadsapp.ui.splash.interactor

import android.content.Context
import com.rainads.rainadsapp.data.ApiCalls
import com.rainads.rainadsapp.data.preferences.PreferenceHelper
import com.rainads.rainadsapp.ui.base.interactor.BaseInteractor
import javax.inject.Inject


class SplashInteractor @Inject constructor(
    private val mContext: Context,
    preferenceHelper: PreferenceHelper,
    apiCalls: ApiCalls
) : BaseInteractor(preferenceHelper, apiCalls),
    SplashMVPInteractor