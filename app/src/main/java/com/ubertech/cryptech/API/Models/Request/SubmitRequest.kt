package com.ubertech.cryptech.API.Models.Request

import com.google.gson.annotations.SerializedName

class SubmitRequest(code: String) {

    @SerializedName("code")
    val code: String = code

}