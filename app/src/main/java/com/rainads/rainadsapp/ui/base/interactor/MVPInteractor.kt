package com.rainads.rainadsapp.ui.base.interactor

interface MVPInteractor {

    fun isUserLoggedIn(): Boolean

    fun getCode(): String?

    fun getBtcAddress(): String?

    fun performUserLogout()

}