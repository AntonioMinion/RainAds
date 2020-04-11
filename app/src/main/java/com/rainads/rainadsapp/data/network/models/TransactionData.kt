package com.rainads.rainadsapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TransactionData(

    @Expose
    @SerializedName("transactionsData")
    var transactionList: List<TransactionResponse>

)