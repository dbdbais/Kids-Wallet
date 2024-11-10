package com.ssafy.kidswallet.data.model

data class UserDataModel(
    val userName: String,
    val gender: String,
    val name: String,
    val balance: Double,
    val role: String,
    val cardCreated: Boolean,
    val accountOpened: Boolean,
    val childOrAdultList: List<String>
)
