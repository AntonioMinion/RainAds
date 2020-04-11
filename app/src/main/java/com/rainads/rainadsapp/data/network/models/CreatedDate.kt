package com.rainads.rainadsapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CreatedDate(

    @Expose
    @SerializedName("_seconds")
    val seconds: Long,

    @Expose
    @SerializedName("_nanoseconds")
    val nanoseconds: Long

)