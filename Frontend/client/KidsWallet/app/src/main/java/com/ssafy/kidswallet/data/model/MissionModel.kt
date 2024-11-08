package com.ssafy.kidswallet.data.model

data class MissionModel(
    val name: String,
    val begDto: BegDto,
    val mission: Mission? = null
)

data class BegDto(
    val begId: Int,
    val begMoney: Int,
    val begContent: String,
    val createAt: String,
    val begAccept: Boolean? // Nullable 처리
)

data class Mission(
    val missionStatus: String,
    val completionPhoto: String? = null, // Nullable 처리
    val completedAt: String? = null, // Nullable 처리
    val createdAt: String,
    val missionContent: String,
    val missionId: Int
)
