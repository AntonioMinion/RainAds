package com.rainads.rainadsapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ReferralsNetwork(

    @Expose
    @SerializedName("networkLvl1")
    var networkLvl1: List<String>,

    @Expose
    @SerializedName("networkLvl2")
    var networkLvl2: List<String>,

    @Expose
    @SerializedName("networkLvl3")
    var networkLvl3: List<String>,

    @Expose
    @SerializedName("networkLvl4")
    var networkLvl4: List<String>,

    @Expose
    @SerializedName("networkLvl5")
    var networkLvl5: List<String>,

    @Expose
    @SerializedName("networkLvl6")
    var networkLvl6: List<String>,

    @Expose
    @SerializedName("networkLvl7")
    var networkLvl7: List<String>,

    @Expose
    @SerializedName("networkLvl8")
    var networkLvl8: List<String>,

    @Expose
    @SerializedName("networkLvl9")
    var networkLvl9: List<String>,

    @Expose
    @SerializedName("networkLvl10")
    var networkLvl10: List<String>
)