package com.ssafy.kidswallet.data.model

data class TogetherListModel(
    val savingContractId: Int,
    val targetTitle: String,
    val currentAmount: Int,
    val dDay: Int
)

data class TogetherListResponse(
    val data: List<TogetherListModel>,
    val message: String?,
    val timestamp: String
)
