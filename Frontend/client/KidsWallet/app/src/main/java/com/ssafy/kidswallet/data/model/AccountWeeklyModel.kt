package com.ssafy.kidswallet.data.model

data class AccountWeeklyModel(
    val curSpentMoney: Int,
    val curIncomeMoney: Int,
    val prevSpentMoney: Int,
    val prevIncomeMoney: Int,
    val prevListSpent: List<Int>,
    val curListSpent: List<Int>,
    val prevListIncome: List<Int>,
    val curListIncome: List<Int>
)

data class AccountWeeklyResponse(
    val data: AccountWeeklyModel,
    val message: String?,
    val timestamp: String?
)
