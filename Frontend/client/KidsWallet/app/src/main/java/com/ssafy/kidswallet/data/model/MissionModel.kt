package com.ssafy.kidswallet.data.model

// 응답 데이터를 나타내는 최상위 클래스
data class MissionResponse(
    val data: List<MissionModel>?,
    val message: String?,
    val timestamp: String?
)

// 각 항목을 나타내는 클래스
data class MissionModel(
    val name: String,
    val begDto: BegDto,
    val mission: Mission? = null
)

// 조르기 관련 데이터를 나타내는 클래스
data class BegDto(
    val begId: Int,
    val begMoney: Int,
    val begContent: String,
    val createAt: List<Int>,
    val begAccept: Boolean?
)

// 미션 데이터를 나타내는 클래스
data class Mission(
    val missionStatus: String,
    val completionPhoto: String? = null,
    val completedAt: List<Int>? = null,
    val createdAt: List<Int>,
    val missionContent: String,
    val missionId: Long
)
