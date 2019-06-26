package com.rainads.rainadsapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AdModel(
        @Expose
        @SerializedName("campaginId") internal val id: String,
        @Expose
        @SerializedName("url") internal val url: String,
        @Expose
        @SerializedName("description") internal val description: String,
        @Expose
        @SerializedName("price") internal val price: String,
        @Expose
        @SerializedName("duration") internal val duration: String,
        @Expose
        @SerializedName("status") internal val status: String,
        @Expose
        @SerializedName("activityStatus") internal val activityStatus: String,
        @Expose
        @SerializedName("countries") internal val countries: List<String>,
        @Expose
        @SerializedName("message") internal val message: String
)