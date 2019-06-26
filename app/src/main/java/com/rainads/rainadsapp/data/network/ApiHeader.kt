package com.rainads.rainadsapp.data.network

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

class ApiHeader @Inject constructor(internal val protectedApiHeader: ProtectedApiHeader) {
    class ProtectedApiHeader @Inject constructor(
        @SerializedName("localId") val accessToken: String?
    )
}