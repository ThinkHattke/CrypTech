package com.ubertech.cryptech.API.Models.Request

import com.google.gson.annotations.SerializedName

class VerifyRequest(secret: String) {

    @SerializedName("secret")
    val secret: String = secret

}