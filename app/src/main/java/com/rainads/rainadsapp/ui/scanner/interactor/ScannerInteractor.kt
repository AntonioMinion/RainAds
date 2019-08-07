package com.rainads.rainadsapp.ui.scanner.interactor

import android.content.Context
import com.rainads.rainadsapp.data.ApiCalls
import com.rainads.rainadsapp.data.preferences.PreferenceHelper
import com.rainads.rainadsapp.ui.base.interactor.BaseInteractor
import javax.inject.Inject

class ScannerInteractor @Inject internal constructor(
        private val mContext: Context,
        preferenceHelper: PreferenceHelper,
        apiCalls: ApiCalls
) :
        BaseInteractor(preferenceHelper = preferenceHelper, apiCalls = apiCalls), IScannerInteractor