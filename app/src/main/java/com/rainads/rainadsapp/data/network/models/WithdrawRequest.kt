package com.rainads.rainadsapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.rainads.rainadsapp.util.TransactionType

data class WithdrawRequest(
        @Expose
        @SerializedName("localId") internal val userId: String,
        @Expose
        @SerializedName("amount") internal val amount: String,
        @Expose
        @SerializedName("transactionType") internal val type: TransactionType,
        @Expose
        @SerializedName("address") internal val address: String
)