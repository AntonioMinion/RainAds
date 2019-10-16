package com.rainads.rainadsapp.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.rainads.rainadsapp.di.PreferenceInfo
import com.rainads.rainadsapp.util.MyConstants
import javax.inject.Inject

class AppPreferenceHelper @Inject constructor(
    context: Context,
    @PreferenceInfo private val prefFileName: String
) : PreferenceHelper {

    companion object {
        private const val PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID"
        private const val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
        private const val PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME"
        private const val PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL"
        private const val PREF_KEY_USER_LOGGED = "PREF_KEY_USER_LOGGED_IN_MODE"
        private const val PREF_KEY_USER_BTC_ADDRESS = "PREF_KEY_USER_BTC_ADDRESS"
        private const val PREF_KEY_LAST_WATCH_TIME = "PREF_KEY_LAST_WATCH_TIME"
    }

    private val mPrefs: SharedPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun getCurrentUserEmail(): String? = mPrefs.getString(PREF_KEY_CURRENT_USER_EMAIL, "Email")

    override fun setCurrentUserEmail(email: String?) = mPrefs.edit { putString(PREF_KEY_CURRENT_USER_EMAIL, email) }

    override fun getAccessToken(): String? = mPrefs.getString(PREF_KEY_ACCESS_TOKEN, "")

    override fun setAccessToken(accessToken: String?) = mPrefs.edit { putString(PREF_KEY_ACCESS_TOKEN, accessToken) }

    override fun getBtcAddress(): String? = mPrefs.getString(PREF_KEY_USER_BTC_ADDRESS, "BTC Address")

    override fun setBtcAddress(btcAddress: String?) = mPrefs.edit { putString(PREF_KEY_USER_BTC_ADDRESS, btcAddress) }

    override fun getCurrentUserId(): Long? {
        val userId = mPrefs.getLong(PREF_KEY_CURRENT_USER_ID, MyConstants.NULL_INDEX)
        return when (userId) {
            MyConstants.NULL_INDEX -> null
            else -> userId
        }
    }

    override fun setCurrentUserId(userId: Long?) {
        val id = userId ?: MyConstants.NULL_INDEX
        mPrefs.edit { putLong(PREF_KEY_CURRENT_USER_ID, id) }
    }

    override fun setUserLoggedIn(isLogged: Boolean) {
        mPrefs.edit { putBoolean(PREF_KEY_USER_LOGGED, isLogged) }
    }

    override fun getUserLoggedIn(): Boolean = mPrefs.getBoolean(PREF_KEY_USER_LOGGED, false)

    override fun setLastWatchedAdTime(time: Long) {
        mPrefs.edit { putLong(PREF_KEY_LAST_WATCH_TIME, time) }
    }


    override fun getLastWatchedAdTime(): Long = mPrefs.getLong(PREF_KEY_LAST_WATCH_TIME, 0)

}