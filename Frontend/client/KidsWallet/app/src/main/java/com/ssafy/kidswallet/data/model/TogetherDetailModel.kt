package com.ssafy.kidswallet.data.model

data class TogetherDetailModel(
    val targetTitle: String,
    val targetImage: String,
    val targetAmount: Int,
    val expiredAt: List<Int>, // 연, 월, 일 순서로 포함된 리스트
    val childAmount: Int?,
    val parentAmount: Int?,
    val dDay: Int
)

data class TogetherDetailResponse(
    val data: TogetherDetailModel,
    val message: String?,
    val timestamp: String
)
