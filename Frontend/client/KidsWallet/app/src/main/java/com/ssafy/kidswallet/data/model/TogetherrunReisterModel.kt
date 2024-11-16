package com.ssafy.kidswallet.data.model

data class TogetherrunReisterModel(
    val parentsId: Int,          // 부모 ID
    val childId: Int,            // 자녀 ID
    val targetTitle: String,     // 목표 제목
    val targetAmount: Int,       // 목표 금액
    val targetDate: String,      // 목표 날짜 (yyyy-MM-dd 형식)
    val parentsContribute: Int,  // 부모 기여 금액
    val childContribute: Int,     // 자녀 기여 금액
    val targetImage: String     // 목표 이미지 (Base64 또는 URL)
)
