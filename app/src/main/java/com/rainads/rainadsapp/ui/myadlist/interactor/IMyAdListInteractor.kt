package com.rainads.rainadsapp.ui.myadlist.interactor

import com.rainads.rainadsapp.data.network.models.User
import com.rainads.rainadsapp.ui.base.interactor.MVPInteractor
import io.reactivex.Observable
import org.json.JSONObject

interface IMyAdListInteractor : MVPInteractor {
    fun getUser(): Observable<User>
    fun changeAdStatus(id: String, newStatus: String): Observable<JSONObject>
}