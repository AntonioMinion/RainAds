package com.rainads.rainadsapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Country(

    @Expose
    @SerializedName("name")
    var name: String,

    @Expose
    @SerializedName("phoneCode")
    var phoneCode: String,

    @Expose
    @SerializedName("alpha2code")
    var countryCode1: String,

    @Expose
    @SerializedName("alpha3code")
    var countryCode2: String,

    var isSelected: Boolean
)