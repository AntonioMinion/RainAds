package com.rainads.rainadsapp.data.network

import com.rainads.rainadsapp.BuildConfig


object ApiEndPoint {
    const val ENDPOINT_SERVER_LOGIN = BuildConfig.BASE_URL + "/loginUser"
    const val ENDPOINT_SERVER_REGISTER = BuildConfig.BASE_URL + "/createUser"
    const val ENDPOINT_GET_USER = BuildConfig.BASE_URL + "/getUser"
    const val ENDPOINT_GET_AD = BuildConfig.BASE_URL + "/getAd"
    const val ENDPOINT_WATCH_AD = BuildConfig.BASE_URL + "/watchAd"
    const val ENDPOINT_CREATE_AD = BuildConfig.BASE_URL + "/createAd"
    const val ENDPOINT_PLAY_AD = BuildConfig.BASE_URL + "/playAd"
    const val ENDPOINT_SATOSHI_LIST = BuildConfig.BASE_URL + "/satoshiList"
    const val ENDPOINT_DEPOSIT = BuildConfig.BASE_URL + "/transferToRainAds"
    const val ENDPOINT_WITHDRAW = BuildConfig.BASE_URL + "/withdrawl"
}