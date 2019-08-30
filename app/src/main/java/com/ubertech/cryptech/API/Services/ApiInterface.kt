package com.ubertech.cryptech.API.Services

import com.ubertech.cryptech.API.Models.Request.LoginRequest
import com.ubertech.cryptech.API.Models.Request.RegistrationRequest
import com.ubertech.cryptech.API.Models.Request.SubmitRequest
import com.ubertech.cryptech.API.Models.Request.VerifyRequest
import com.ubertech.cryptech.API.Models.Response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {


    // Auth
    @POST("auth/login")
    fun requestLogin(@Body params: LoginRequest): Call<LoginResponse>

    @POST("auth/register")
    fun requestRegister(@Body params: RegistrationRequest): Call<RegistrationResponse>

    @POST("core/verify")
    fun requestVerification(@Header("x-access-token") auth: String,
                            @Body params: VerifyRequest): Call<VerifyResponse>


    // Main features
    @GET("core/level")
    fun requestLevel(@Header("x-access-token") auth: String): Call<LevelResponse>

    @POST("level/submit")
    fun submitAnswer(@Header("x-access-token") auth: String, @Body params: SubmitRequest): Call<VerifyResponse>

    @GET("core/leaderboards")
    fun requestLeaderBoard(@Header("x-access-token") auth: String): Call<List<leaderboardUser>>


}