package com.ubertech.cryptech.API.Models.Request

import com.google.gson.annotations.SerializedName

class RegistrationRequest(email: String, full_name: String, mobile: String, password: String,
                          college :String, year: String, section: String, registration_number: String) {


    @SerializedName("email")
    val email: String = email

    @SerializedName("full_name")
    val full_name: String = full_name

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

    @SerializedName("registration_number")
    val registration_number: String = registration_number

}