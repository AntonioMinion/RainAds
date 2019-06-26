package com.rainads.rainadsapp.data.network

import com.google.gson.Gson
import com.rainads.rainadsapp.data.ApiCalls
import com.rainads.rainadsapp.data.network.models.*
import com.rainads.rainadsapp.data.preferences.PreferenceHelper
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observable
import org.json.JSONObject
import javax.inject.Inject


class AppApiHelper @Inject constructor(private val preferenceHelper: PreferenceHelper) : ApiCalls {


    //USER
    override fun registerCall(request: RegisterRequest): Observable<User> =
            Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_REGISTER)
                    .addBodyParameter(request)
                    .build()
                    .getObjectObservable(User::class.java)


    override fun loginCall(request: LoginRequest): Observable<User> =
            Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_LOGIN)
                    .addBodyParameter(request)
                    .build()
                    .getObjectObservable(User::class.java)

    override fun getUser(request: String): Observable<User> =
            Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_USER)
                    .addHeaders(ApiHeader.ProtectedApiHeader(preferenceHelper.getAccessToken()))
                    .build()
                    .getObjectObservable(User::class.java)

    //ADS
    override fun createAd(request: NewAdRequest): Observable<String> =
            Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CREATE_AD)
                    .addStringBody(Gson().toJson(request))
                    .setContentType("application/json")
                    .addHeaders(ApiHeader.ProtectedApiHeader(preferenceHelper.getAccessToken()))
                    .build().stringObservable

    override fun getAd(): Observable<AdModel> =
            Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_AD)
                    .addHeaders(ApiHeader.ProtectedApiHeader(preferenceHelper.getAccessToken()))
                    .build()
                    .getObjectObservable(AdModel::class.java)

    override fun watchAd(request: WatchAdRequest): Observable<String> =
            Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_WATCH_AD)
                    .addBodyParameter(request)
                    .addHeaders(ApiHeader.ProtectedApiHeader(preferenceHelper.getAccessToken()))
                    .build()
                    .stringObservable

    override fun changeAdStatus(request: PlayAdRequest): Observable<JSONObject> =
            Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_PLAY_AD)
                    .addBodyParameter(request)
                    .addHeaders(ApiHeader.ProtectedApiHeader(preferenceHelper.getAccessToken()))
                    .build()
                    .jsonObjectObservable

}