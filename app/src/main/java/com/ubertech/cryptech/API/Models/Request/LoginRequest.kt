package com.ubertech.cryptech.API.Models.Request

import com.google.gson.annotations.SerializedName

class LoginRequest(emailId: String, password: String) {

    @SerializedName("emailId")
    val emailId: String = emailId

    @SerializedName("password")
    val password: String = password

}