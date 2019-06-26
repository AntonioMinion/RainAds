package com.rainads.rainadsapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WatchAdRequest(
    @Expose
    @SerializedName("campaginId") internal val adId: String
)