package com.rainads.rainadsapp.data

import com.rainads.rainadsapp.data.network.models.*
import io.reactivex.Observable
import org.json.JSONObject

interface ApiCalls {
    fun loginCall(request: LoginRequest): Observable<LoginResponse>
    fun registerCall(request: RegisterRequest): Observable<String>
    fun getUser(request: String): Observable<User>
    fun getAd(): Observable<AdModel>
    fun createAd(request: NewAdRequest): Observable<String>
    fun sendDepositRequest(request: DepositRequest): Observable<String>
    fun sendWithdrawRequest(request: WithdrawRequest): Observable<String>
    fun watchAd(request: WatchAdRequest): Observable<String>
    fun changeAdStatus(request: PlayAdRequest): Observable<JSONObject>
    fun getPointsList(): Observable<List<PointsResponse>>
    fun resendConfirmEmail(request: ResendEmailRequest): Observable<String>
    fun resetPassword(request: ResetPasswordRequest): Observable<String>
}