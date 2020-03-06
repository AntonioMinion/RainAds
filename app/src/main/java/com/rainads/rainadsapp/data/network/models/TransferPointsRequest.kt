package com.rainads.rainadsapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TransferPointsRequest (
    @Expose
    @SerializedName("amount") internal val amount: String,
    @Expose
    @SerializedName("recipient") internal val receiverId: String
)