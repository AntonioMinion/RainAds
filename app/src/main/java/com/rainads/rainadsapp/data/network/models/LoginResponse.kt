package com.rainads.rainadsapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(

        @Expose
        @SerializedName("user")
        var userData: User? = null,

        @Expose
        @SerializedName("idToken")
        var idToken: String,

        @Expose
        @SerializedName("message")
        var message: String


)
