package com.rainads.rainadsapp.ui.base.interactor

import com.rainads.rainadsapp.data.network.models.User
import io.reactivex.Observable
import java.util.*

interface MVPInteractor {

    fun isUserLoggedIn(): Boolean

    fun getCode(): String?

    fun getBtcAddress(): String?

    fun performUserLogout()

    fun downloadUser(): Observable<User>

}