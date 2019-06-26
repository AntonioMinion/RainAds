package com.rainads.rainadsapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PlayAdRequest(
    @Expose
    @SerializedName("campaginId") internal val campaignId: String,
    @Expose
    @SerializedName("activityStatus") internal val newActivityStatus: String
)