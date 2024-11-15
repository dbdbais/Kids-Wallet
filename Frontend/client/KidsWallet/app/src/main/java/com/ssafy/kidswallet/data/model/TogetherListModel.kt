package com.ssafy.kidswallet.data.model

data class TogetherListModel(
    val togetherRunId: Int,
    val targetTitle: String,
    val targetAmount: Int,
    val dDay: Int,
    val isAccept : Boolean
)

data class TogetherListResponse(
    val data: List<TogetherListModel>,
    val message: String?,
    val timestamp: String
)
