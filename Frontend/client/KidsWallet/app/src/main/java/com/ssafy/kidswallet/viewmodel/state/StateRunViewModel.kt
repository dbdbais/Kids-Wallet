package com.ssafy.kidswallet.ui.screens.run.viewmodel.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class StateRunViewModel : ViewModel() {
    // 목표 텍스트와 날짜 텍스트를 관리하는 전역 상태
    var goalText by mutableStateOf("")
        private set

    var selectedDateText by mutableStateOf("")
        private set

    // 목표와 날짜를 설정하는 함수
    fun setGoalAndDate(goal: String, date: String) {
        goalText = goal
        selectedDateText = date
    }
}
