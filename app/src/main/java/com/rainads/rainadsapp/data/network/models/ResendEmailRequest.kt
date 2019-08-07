package com.rainads.rainadsapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.rainads.rainadsapp.util.TransactionType

data class ResendEmailRequest(
        @Expose
        @SerializedName("idToken") internal val token: String
)