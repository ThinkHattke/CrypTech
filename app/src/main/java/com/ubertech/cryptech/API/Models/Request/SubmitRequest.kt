package com.ubertech.cryptech.API.Models.Request

import com.google.gson.annotations.SerializedName

class SubmitRequest(answer: String) {

    @SerializedName("answer")
    val answer: String = answer

}