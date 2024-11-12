package com.ssafy.kidswallet.data.model

data class AccountModel(
    val data: List<TransactionModel>,
    val message: String?,
    val timestamp: String
)

data class TransactionModel(
    val accountId: String,
    val message: String,
    val transactionType: String,
    val amount: Int,
    val transactionDate: List<Int>
)

