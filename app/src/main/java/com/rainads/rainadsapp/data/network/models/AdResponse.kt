package com.rainads.rainadsapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AdResponse(
    @Expose
    @SerializedName("campaginId")
    var id: String?,
    @Expose
    @SerializedName("campaginToSend")
    var theAd: AdModel?,
    @Expose
    @SerializedName("message")
    val message: String?
)