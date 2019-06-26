package com.rainads.rainadsapp.ui.base.interactor

import com.rainads.rainadsapp.data.ApiCalls
import com.rainads.rainadsapp.data.preferences.PreferenceHelper

open class BaseInteractor() : MVPInteractor {

    protected lateinit var preferenceHelper: PreferenceHelper
    protected lateinit var apiCalls: ApiCalls

    constructor(preferenceHelper: PreferenceHelper, apiCalls: ApiCalls) : this() {
        this.preferenceHelper = preferenceHelper
        this.apiCalls = apiCalls
    }

    override fun isUserLoggedIn() = this.preferenceHelper.getUserLoggedIn()

    override fun performUserLogout() = preferenceHelper.let {
        it.setCurrentUserId(null)
        it.setAccessToken(null)
        it.setCurrentUserEmail(null)
        it.setUserLoggedIn(false)
        it.setBtcAddress(null)
    }

    override fun getCode() = this.preferenceHelper.getAccessToken()

    override fun getBtcAddress(): String? = this.preferenceHelper.getBtcAddress()

}