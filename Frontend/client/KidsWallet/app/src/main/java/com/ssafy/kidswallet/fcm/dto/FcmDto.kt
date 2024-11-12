package com.ssafy.kidswallet.fcm.dto

import com.google.gson.annotations.SerializedName

data class FcmDto(
    @SerializedName("userId") // 실제 json 데이터의 키 값
    var userId: Long?, // 기본값 추가
    @SerializedName("tokenValue") // 실제 json 데이터의 키 값
    var tokenValue: String? // 기본값 추가
)

