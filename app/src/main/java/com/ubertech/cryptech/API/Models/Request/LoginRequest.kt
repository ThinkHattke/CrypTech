package com.ubertech.cryptech.API.Models.Request

import com.google.gson.annotations.SerializedName

class LoginRequest(mobile: String, password: String) {

    @SerializedName("mobile")
    val mobile: String = mobile

    @SerializedName("password")
    val password: String = password

}