package com.rainads.rainadsapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.rainads.rainadsapp.util.TransactionType

data class ResetPasswordRequest(
        @Expose
        @SerializedName("email") internal val email: String
)