package com.ssafy.kidswallet.data.network

import com.ssafy.kidswallet.data.model.ApiResponse
import com.ssafy.kidswallet.data.model.LoginModel
import com.ssafy.kidswallet.data.model.SignUpModel
import com.ssafy.kidswallet.data.model.UserDataModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("user/register")
    suspend fun registerUser(@Body signUpModel: SignUpModel): Response<Any>

    @POST("user/login")
    suspend fun loginUser(@Body loginModel: LoginModel): Response<ApiResponse>
}