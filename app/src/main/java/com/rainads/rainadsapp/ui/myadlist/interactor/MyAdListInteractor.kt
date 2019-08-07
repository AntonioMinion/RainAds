package com.rainads.rainadsapp.ui.myadlist.interactor

import android.content.Context
import com.rainads.rainadsapp.data.ApiCalls
import com.rainads.rainadsapp.data.network.models.PlayAdRequest
import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.data.preferences.PreferenceHelper
import com.rainads.rainadsapp.ui.base.interactor.BaseInteractor
import io.reactivex.Observable
import org.json.JSONObject
import javax.inject.Inject

class MyAdListInteractor @Inject internal constructor(
    private val mContext: Context,
    preferenceHelper: PreferenceHelper,
    apiCalls: ApiCalls
) :
    BaseInteractor(preferenceHelper, apiCalls),
    IMyAdListInteractor {
    override fun changeAdStatus(id: String, newStatus: String): Observable<JSONObject> = apiCalls.changeAdStatus(PlayAdRequest(id, newStatus))

    override fun getUser(): Observable<User> = apiCalls.getUser(preferenceHelper.getAccessToken()!!)
}