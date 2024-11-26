package com.ssafy.kidswallet.data.model

data class TogetherDetailResponse(
    val data: TogetherDetailModel?,
    val message: String?,
    val timestamp: String
)

data class TogetherDetailModel(
    val savingContractId: Int,
    val targetTitle: String,
    val targetImage: String,
    val targetAmount: Int,
    val expiredAt: List<Int>,
    val childAmount: Int,
    val childGoalAmount: Int,
    val childName: String,
    val parentAmount: Int,
    val parentGoalAmount: Int,
    val parentName: String,
    val dDay: Int,
    val isAccept: Boolean
)
