package com.ssafy.kidswallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.BegDto
import com.ssafy.kidswallet.data.model.Mission
import com.ssafy.kidswallet.data.model.MissionModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BeggingMissionViewModel : ViewModel() {
    private val _missionList = MutableStateFlow<List<MissionModel>>(emptyList())
    val missionList: StateFlow<List<MissionModel>> = _missionList

    fun fetchMissionList() {
        loadMissionList()
    }

    private fun loadMissionList() {
        viewModelScope.launch {
            // 예시: 직접 데이터 생성
            val missionList = listOf(
                // 조르기 수락 후 미션을 수행 했는데 미션 실패(missionStatus = fail) - 표시X
                MissionModel(
                    name = "오성혁",
                    begDto = BegDto(
                        begId = 1,
                        begMoney = 1000,
                        begContent = "Create Beg Test 1",
                        createAt = "2024-11-06T11:37:34.987481",
                        begAccept = true
                    ),
                    mission = Mission(
                        missionStatus = "fail",
                        completionPhoto = "photo_url_1",
                        completedAt = "2024-11-06T11:39:20.134112",
                        createdAt = "2024-11-06T11:37:35.125969",
                        missionContent = "Assign Mission Test 1",
                        missionId = 1
                    )
                ),
                // 조르기 수락 후 미션을 수행 했는데 미션 성공(missionStatus = complete) - 승인
                MissionModel(
                    name = "홍길동",
                    begDto = BegDto(
                        begId = 2,
                        begMoney = 2000,
                        begContent = "Create Beg Test 2",
                        createAt = "2024-11-06T12:00:00.000000",
                        begAccept = true
                    ),
                    mission = Mission(
                        missionStatus = "complete",
                        completionPhoto = "photo_url_2",
                        completedAt = "2024-11-06T12:30:00.134112",
                        createdAt = "2024-11-06T12:00:30.125969",
                        missionContent = "Assign Mission Test 2",
                        missionId = 2
                    )
                ),
                // 조르기 거절(begAccept false) - 표시X
                MissionModel(
                    name = "이영희",
                    begDto = BegDto(
                        begId = 3,
                        begMoney = 1500,
                        begContent = "Create Beg Test 3",
                        createAt = "2024-11-06T13:00:00.000000",
                        begAccept = false
                    ),
                    mission = null
                ),
                // 조르기 요청 후 수락만 함(begAccept true, mission null) - 대기
                MissionModel(
                    name = "박민수",
                    begDto = BegDto(
                        begId = 4,
                        begMoney = 1800,
                        begContent = "Create Beg Test 4",
                        createAt = "2024-11-06T14:00:00.000000",
                        begAccept = true
                    ),
                    mission = null
                ),
                // 조르기 요청만 한 경우(begAccept null, mission null) - 대기
                MissionModel(
                    name = "김철수",
                    begDto = BegDto(
                        begId = 5,
                        begMoney = 1700,
                        begContent = "Create Beg Test 5",
                        createAt = "2024-11-06T15:00:00.000000",
                        begAccept = null
                    ),
                    mission = null
                ),
                // 조르기 수락 후 미션이 주어진 경우(미션 수행 X)(missionStatus proceed) - 미션 수행
                MissionModel(
                    name = "최지훈",
                    begDto = BegDto(
                        begId = 6,
                        begMoney = 2200,
                        begContent = "Create Beg Test 6",
                        createAt = "2024-11-06T16:00:00.000000",
                        begAccept = true
                    ),
                    mission = Mission(
                        missionStatus = "proceed",
                        completionPhoto = null,
                        completedAt = null,
                        createdAt = "2024-11-06T16:30:00.125969",
                        missionContent = "Assign Mission Test 3",
                        missionId = 3
                    )
                ),
                // 조르기 수락 후 미션을 수행 했는데 미션 실패(missionStatus = fail)
                MissionModel(
                    name = "오성혁2",
                    begDto = BegDto(
                        begId = 1,
                        begMoney = 1000,
                        begContent = "Create Beg Test 1",
                        createAt = "2024-11-06T11:37:34.987481",
                        begAccept = true
                    ),
                    mission = Mission(
                        missionStatus = "fail",
                        completionPhoto = "photo_url_1",
                        completedAt = "2024-11-06T11:39:20.134112",
                        createdAt = "2024-11-06T11:37:35.125969",
                        missionContent = "Assign Mission Test 1",
                        missionId = 1
                    )
                ),
                // 조르기 수락 후 미션을 수행 했는데 미션 성공(missionStatus = complete)
                MissionModel(
                    name = "홍길동2",
                    begDto = BegDto(
                        begId = 2,
                        begMoney = 2000,
                        begContent = "Create Beg Test 2",
                        createAt = "2024-11-06T12:00:00.000000",
                        begAccept = true
                    ),
                    mission = Mission(
                        missionStatus = "complete",
                        completionPhoto = "photo_url_2",
                        completedAt = "2024-11-06T12:30:00.134112",
                        createdAt = "2024-11-06T12:00:30.125969",
                        missionContent = "Assign Mission Test 2",
                        missionId = 2
                    )
                ),
                // 조르기 수락 후 미션을 수행 했는데 미션 성공(missionStatus = complete)
                MissionModel(
                    name = "최정길8",
                    begDto = BegDto(
                        begId = 2,
                        begMoney = 2000,
                        begContent = "Create Beg Test 2",
                        createAt = "2024-11-06T12:00:00.000000",
                        begAccept = true
                    ),
                    mission = Mission(
                        missionStatus = "submit",
                        completionPhoto = "photo_url_10",
                        completedAt = null,
                        createdAt = "2024-11-06T12:00:30.125969",
                        missionContent = "Assign Mission Test 2",
                        missionId = 2
                    )
                ),
                // 조르기 거절(begAccept false)
                MissionModel(
                    name = "이영희2",
                    begDto = BegDto(
                        begId = 3,
                        begMoney = 1500,
                        begContent = "Create Beg Test 3",
                        createAt = "2024-11-06T13:00:00.000000",
                        begAccept = false
                    ),
                    mission = null
                ),
                // 조르기 요청 후 수락만 함(begAccept true, mission null)
                MissionModel(
                    name = "박민수2",
                    begDto = BegDto(
                        begId = 4,
                        begMoney = 1800,
                        begContent = "Create Beg Test 4",
                        createAt = "2024-11-06T14:00:00.000000",
                        begAccept = true
                    ),
                    mission = null
                ),
                // 조르기 요청만 한 경우(begAccept null, mission null)
                MissionModel(
                    name = "김철수2",
                    begDto = BegDto(
                        begId = 5,
                        begMoney = 1700,
                        begContent = "Create Beg Test 5",
                        createAt = "2024-11-06T15:00:00.000000",
                        begAccept = null
                    ),
                    mission = null
                ),
                // 조르기 수락 후 미션이 주어진 경우(미션 수행 X)(missionStatus proceedd)
                MissionModel(
                    name = "최지훈2",
                    begDto = BegDto(
                        begId = 6,
                        begMoney = 2200,
                        begContent = "Create Beg Test 6",
                        createAt = "2024-11-06T16:00:00.000000",
                        begAccept = true
                    ),
                    mission = Mission(
                        missionStatus = "proceed",
                        completionPhoto = null,
                        completedAt = null,
                        createdAt = "2024-11-06T16:30:00.125969",
                        missionContent = "Assign Mission Test 3",
                        missionId = 3
                    )
                ),
                // 조르기 수락 후 미션을 수행 했는데 미션 실패(missionStatus = fail)
                MissionModel(
                    name = "오성혁3",
                    begDto = BegDto(
                        begId = 1,
                        begMoney = 1000,
                        begContent = "Create Beg Test 1",
                        createAt = "2024-11-06T11:37:34.987481",
                        begAccept = true
                    ),
                    mission = Mission(
                        missionStatus = "fail",
                        completionPhoto = "photo_url_1",
                        completedAt = "2024-11-06T11:39:20.134112",
                        createdAt = "2024-11-06T11:37:35.125969",
                        missionContent = "Assign Mission Test 1",
                        missionId = 1
                    )
                ),
                // 조르기 수락 후 미션을 수행 했는데 미션 성공(missionStatus = complete)
                MissionModel(
                    name = "홍길동3",
                    begDto = BegDto(
                        begId = 2,
                        begMoney = 2000,
                        begContent = "Create Beg Test 2",
                        createAt = "2024-11-06T12:00:00.000000",
                        begAccept = true
                    ),
                    mission = Mission(
                        missionStatus = "complete",
                        completionPhoto = "photo_url_2",
                        completedAt = "2024-11-06T12:30:00.134112",
                        createdAt = "2024-11-06T12:00:30.125969",
                        missionContent = "Assign Mission Test 2",
                        missionId = 2
                    )
                ),
                // 조르기 거절(begAccept false)
                MissionModel(
                    name = "이영희3",
                    begDto = BegDto(
                        begId = 3,
                        begMoney = 1500,
                        begContent = "Create Beg Test 3",
                        createAt = "2024-11-06T13:00:00.000000",
                        begAccept = false
                    ),
                    mission = null
                ),
                // 조르기 요청 후 수락만 함(begAccept true, mission null)
                MissionModel(
                    name = "박민수3",
                    begDto = BegDto(
                        begId = 4,
                        begMoney = 1800,
                        begContent = "Create Beg Test 4",
                        createAt = "2024-11-06T14:00:00.000000",
                        begAccept = true
                    ),
                    mission = null
                ),
                // 조르기 요청만 한 경우(begAccept null, mission null)
                MissionModel(
                    name = "김철수3",
                    begDto = BegDto(
                        begId = 5,
                        begMoney = 1700,
                        begContent = "Create Beg Test 5",
                        createAt = "2024-11-06T15:00:00.000000",
                        begAccept = null
                    ),
                    mission = null
                ),
                // 조르기 수락 후 미션이 주어진 경우(미션 수행 X)(missionStatus proceedd)
                MissionModel(
                    name = "최지훈3",
                    begDto = BegDto(
                        begId = 6,
                        begMoney = 2200,
                        begContent = "Create Beg Test 6",
                        createAt = "2024-11-06T16:00:00.000000",
                        begAccept = true
                    ),
                    mission = Mission(
                        missionStatus = "proceed",
                        completionPhoto = null,
                        completedAt = null,
                        createdAt = "2024-11-06T16:30:00.125969",
                        missionContent = "Assign Mission Test 3",
                        missionId = 3
                    )
                ),
            )
            _missionList.value = missionList
        }
    }

}