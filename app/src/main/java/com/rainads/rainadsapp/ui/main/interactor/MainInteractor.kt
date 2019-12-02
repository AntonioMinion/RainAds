package com.rainads.rainadsapp.ui.main.interactor

import android.content.Context
import android.os.SystemClock
import com.rainads.rainadsapp.R
import com.rainads.rainadsapp.data.ApiCalls
import com.rainads.rainadsapp.data.network.models.AdModel
import com.rainads.rainadsapp.data.preferences.PreferenceHelper
import com.rainads.rainadsapp.ui.base.interactor.BaseInteractor
import io.reactivex.Observable
import javax.inject.Inject

class MainInteractor @Inject internal constructor(
    private val mContext: Context,
    preferenceHelper: PreferenceHelper,
    apiCalls: ApiCalls
) :
    BaseInteractor(preferenceHelper, apiCalls),
    MainMVPInteractor {

    override fun downloadAd(): Observable<AdModel> {
/*
        val elapsedSeconds =
            (SystemClock.elapsedRealtime() - preferenceHelper.getLastWatchedAdTime()) / 1000.0

        if (elapsedSeconds < 300) {
            return Observable.error(
                Throwable(
                    mContext.getString(
                        R.string.ad_time_error,
                        (300 - elapsedSeconds.toInt()).toString()
                    )
                )
            )
        }
*/

        return apiCalls.getAd()
    }

    override fun downloadUser() = apiCalls.getUser(preferenceHelper.getAccessToken()!!)

    override fun checkTimeForAd(): Observable<Boolean> {
        val elapsedSeconds =
            (SystemClock.elapsedRealtime() - preferenceHelper.getLastWatchedAdTime()) / 1000.0

        return if (elapsedSeconds < 300) Observable.just(true) else Observable.just(false)
    }
}