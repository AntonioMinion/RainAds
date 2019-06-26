package com.rainads.rainadsapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @Expose
    @SerializedName("email") internal val email: String,
    @Expose
    @SerializedName("password") internal val password: String,
    @Expose
    @SerializedName("country") internal val country: String,
    @Expose
    @SerializedName("referredBy") internal val referredBy: String
)