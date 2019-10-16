package com.rainads.rainadsapp.ui.main.interactor

import com.rainads.rainadsapp.data.network.models.AdModel
import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.ui.base.interactor.MVPInteractor
import io.reactivex.Observable

interface MainMVPInteractor : MVPInteractor {
    fun downloadAd(): Observable<AdModel>
    fun downloadUser(): Observable<User>
    fun checkTimeForAd(): Observable<Boolean>

}