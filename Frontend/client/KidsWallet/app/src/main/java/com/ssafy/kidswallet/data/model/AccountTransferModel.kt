package com.ssafy.kidswallet.data.model

data class AccountTransferModel(
    val fromId: String,
    val toId: String,
    val message: String?,
    val amount: Int
)
