package com.rainads.rainadsapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.rainads.rainadsapp.util.TransactionStatus

data class TransactionResponse(

    @Expose
    @SerializedName("transactionId")
    var transactionId: String,

    @Expose
    @SerializedName("localId")
    var localId: String,

    @Expose
    @SerializedName("amount")
    var amount: String,

    @Expose
    @SerializedName("address")
    var address: String,

    @Expose
    @SerializedName("transactionType")
    var transactionType: String,

    @Expose
    @SerializedName("status")
    var status: TransactionStatus,

    @Expose
    @SerializedName("createdDate")
    var createdDate: CreatedDate,

    @Expose
    @SerializedName("TxId")
    var txId: String

)