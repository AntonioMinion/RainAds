package com.rainads.rainadsapp.ui.initial.interactor

import com.rainads.rainadsapp.data.network.models.Country
import com.rainads.rainadsapp.data.network.models.LoginResponse
import com.rainads.rainadsapp.data.network.models.ResendEmailRequest
import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.ui.base.interactor.MVPInteractor
import io.reactivex.Observable

interface InitialMVPInteractor : MVPInteractor {

    fun loginApiCall(email: String, password: String): Observable<LoginResponse>

    fun registerApiCall(email: String, password: String, country: String, referral: String): Observable<String>

    fun resendEmail(request: ResendEmailRequest): Observable<String>

    fun updateUserInSharedPref(user: User, loggedIn: Boolean)

    fun getCountries(): Observable<List<Country>>
}