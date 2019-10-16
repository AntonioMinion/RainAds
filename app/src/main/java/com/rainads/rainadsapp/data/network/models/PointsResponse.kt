package com.rainads.rainadsapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.rainads.rainadsapp.util.CustomModelDisplayName

data class PointsResponse(
        @Expose
        @SerializedName("satoshi")
        var points: Int,

        @Expose
        @SerializedName("seconds")
        var duration: Int

) : CustomModelDisplayName {
    override val displayName: String
        get() = duration.toString().plus(" seconds/").plus(points.toString()).plus(" points")

}