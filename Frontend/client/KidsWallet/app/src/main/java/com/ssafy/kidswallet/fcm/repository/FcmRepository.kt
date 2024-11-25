package com.ssafy.kidswallet.fcm.repository

import android.util.Log
import com.ssafy.kidswallet.data.network.RetrofitClient
import com.ssafy.kidswallet.fcm.dto.FcmDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FcmRepository {
    fun sendTokenToServer(userId: Long?,token: String?) {
        val requestDto = FcmDto(userId,token);

        RetrofitClient.apiService.sendTokenToServer(requestDto).enqueue(object : Callback<FcmDto> {
            override fun onResponse(call: Call<FcmDto>, response: Response<FcmDto>) {
                if (response.isSuccessful) {
                    Log.d("FCM", "Success: ${response.body().toString()}")
                } else {
                    val errorBody = response.errorBody()?.string() // 에러 내용을 문자열로 변환
                    Log.d("FCM", "Failed: ${errorBody ?: "Unknown error"}")
                }
            }

            override fun onFailure(call: Call<FcmDto>, t: Throwable) {
                Log.d("FCM", "Error: ${t.message}")
            }
        })
    }
}