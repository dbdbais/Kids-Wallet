package com.ssafy.kidswallet.data.model

data class RegularListModel(
    val amount: Int,
    val depositDay: Int,
    val startDate: List<Int>,
    val endDate: List<Int>
)

data class RegularListApiResponse(
    val data: List<RegularListModel>?,
    val message: String?,
    val timestamp: String
)
