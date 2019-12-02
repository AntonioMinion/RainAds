package com.rainads.rainadsapp.ui.initial.interactor

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.internal.`$Gson$Types`
import com.rainads.rainadsapp.data.ApiCalls
import com.rainads.rainadsapp.data.network.models.*
import com.rainads.rainadsapp.data.preferences.PreferenceHelper
import com.rainads.rainadsapp.ui.base.interactor.BaseInteractor
import com.rainads.rainadsapp.util.AppUtils
import com.rainads.rainadsapp.util.MyConstants
import io.reactivex.Observable
import okhttp3.ResponseBody
import javax.inject.Inject

class InitialInteractor @Inject internal constructor(
        private val mContext: Context,
        preferenceHelper: PreferenceHelper,
        apiCalls: ApiCalls
) :
        BaseInteractor(preferenceHelper, apiCalls),
        InitialMVPInteractor {

    override fun getCountries(): Observable<List<Country>> {

        val builder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        val gson = builder.create()
        val type = `$Gson$Types`.newParameterizedTypeWithOwner(null, List::class.java, Country::class.java)
        var countries = gson.fromJson<List<Country>>(
                AppUtils.loadJSONFromAsset(
                        mContext,
                        MyConstants.COUNTRY_DATABASE
                ),
                type
        )
        return Observable.just(countries)
    }

    override fun registerApiCall(email: String, password: String, country: String, referral: String): Observable<String> =
            apiCalls.registerCall(
                    RegisterRequest(
                            email = email,
                            password = password,
                            country = country,
                            referredBy = referral
                    )
            )

    override fun loginApiCall(email: String, password: String) =
            apiCalls.loginCall(LoginRequest(email = email, password = password))


    override fun updateUserInSharedPref(user: User, loggedIn: Boolean) =
            preferenceHelper.let {
                it.setAccessToken(user.token)
                it.setBtcAddress(user.btcAddress)
                it.setCurrentUserEmail(user.email)
                it.setUserLoggedIn(loggedIn)
            }

    override fun resendEmail(request: ResendEmailRequest) =
            apiCalls.resendConfirmEmail(request)

    override fun resetPassword(request: ResetPasswordRequest): Observable<String>  =
            apiCalls.resetPassword(request)
}