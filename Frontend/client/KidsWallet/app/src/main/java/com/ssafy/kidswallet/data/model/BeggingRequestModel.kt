package com.ssafy.kidswallet.data.model

data class BeggingRequestModel(
    val userId: String,
    val toUserId: String,
    val beggingMessage: String,
    val beggingMoney: Int,
)
