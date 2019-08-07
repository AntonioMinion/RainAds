package com.rainads.rainadsapp.data

import com.rainads.rainadsapp.data.network.models.*
import io.reactivex.Observable
import org.json.JSONObject

interface ApiCalls {
    fun loginCall(request: LoginRequest): Observable<User>
    fun registerCall(request: RegisterRequest): Observable<User>
    fun getUser(request: String): Observable<User>
    fun getAd(): Observable<AdModel>
    fun createAd(request: NewAdRequest): Observable<String>
    fun sendDepositRequest(request: DepositRequest): Observable<String>
    fun sendWithdrawRequest(request: WithdrawRequest): Observable<String>
    fun watchAd(request: WatchAdRequest): Observable<String>
    fun changeAdStatus(request: PlayAdRequest): Observable<JSONObject>
    fun getSatoshiList(): Observable<List<SatoshiResponse>>
}