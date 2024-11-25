package com.ssafy.kidswallet.data.model

data class TogetherCompleteListModel(
    val togetherRunId: Int,
    val targetTitle: String,
    val targetAmount: Int,
    val dDay: Int,
    val isAccept: Boolean
)

data class TogetherCompleteListResponse(
    val data: List<TogetherCompleteListModel>?,
    val message: String?,
    val timestamp: String
)
