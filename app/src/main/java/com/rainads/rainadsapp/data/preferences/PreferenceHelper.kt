package com.rainads.rainadsapp.data.preferences

interface PreferenceHelper {

    fun getUserLoggedIn(): Boolean

    fun setUserLoggedIn(isLogged: Boolean)

    fun getCurrentUserId(): Long?

    fun setCurrentUserId(userId: Long?)

    fun getCurrentUserEmail(): String?

    fun setCurrentUserEmail(email: String?)

    fun getAccessToken(): String?

    fun setAccessToken(accessToken: String?)

    fun getBtcAddress(): String?

    fun setBtcAddress(btcAddress: String?)

    fun setLastWatchedAdTime(time: Long)

    fun getLastWatchedAdTime() : Long

}