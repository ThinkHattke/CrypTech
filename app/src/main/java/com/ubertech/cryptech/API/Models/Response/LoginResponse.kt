package com.ubertech.cryptech.API.Models.Response

import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("success")
    val success: String? = null

    @SerializedName("jwt")
    val jwt: String? = null

    @SerializedName("verified")
    val verified: String? = null

}