package com.rainads.rainadsapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(

    @Expose
    @SerializedName("referral")
    var token: String? = null,

    @Expose
    @SerializedName("email")
    var email: String? = null,

    @Expose
    @SerializedName("country")
    var country: String? = null,

    @Expose
    @SerializedName("btcAddress")
    var btcAddress: String? = null,

    @Expose
    @SerializedName("balance")
    var balance: String? = null,

    @Expose
    @SerializedName("btcBalance")
    var btcBalance: String? = null,

    @Expose
    @SerializedName("myCampagins")
    var myCampaigns: List<AdModel>? = null,

    @Expose
    @SerializedName("referralsNetwork")
    var referralsNetwork: ReferralsNetwork? = null

)
