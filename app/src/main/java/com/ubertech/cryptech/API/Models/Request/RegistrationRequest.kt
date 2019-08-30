package com.ubertech.cryptech.API.Models.Request

import com.google.gson.annotations.SerializedName

class RegistrationRequest(emailId: String, name: String, mobile: String, password: String,
                          college :String, year: String, section: String, reg: String) {


    @SerializedName("emailId")
    val emailId: String = emailId

    @SerializedName("name")
    val name: String = name

    @SerializedName("mobile")
    val mobile: String = mobile

    @SerializedName("password")
    val password: String = password

    @SerializedName("college")
    val college: String = college

    @SerializedName("year")
    val year: String = year

    @SerializedName("section")
    val section: String = section

    @SerializedName("reg")
    val reg: String = reg

}