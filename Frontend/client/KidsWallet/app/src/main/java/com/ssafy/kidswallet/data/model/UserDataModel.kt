package com.ssafy.kidswallet.data.model

data class ApiResponse(
    val data: UserDataModel?,
    val message: String?,
    val timestamp: String?
)

data class UserDataModel(
    val userId: Int,
    val userName: String?,
    val userGender: String?,
    val userRealName: String?,
    val userMoney: Int,
    val userRole: String?,
    val userBirth: List<Int>?,
    val hasCard: Boolean,
    val representAccountId: String?,
    val relations: List<Relation>?
)

data class Relation(
    val userGender: String?,
    val userName: String?,
    val userId: Int
)