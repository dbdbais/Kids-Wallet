package com.ssafy.kidswallet.viewmodel.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class StateRunMoneyViewModel : ViewModel() {
    var togetherGoalMoney by mutableStateOf(0)
        private set

    var childGoalMoney by mutableStateOf(0)
        private set

    var parentGoalMoney by mutableStateOf(0)
        private set

    fun setGoalAndDate(togetherGoal: Int, childGoal: Int, parentGoal: Int) {
        togetherGoalMoney = togetherGoal
        childGoalMoney = childGoal
        parentGoalMoney = parentGoal
    }

    // 목표 금액 초기화 메서드
    fun resetGoals() {
        togetherGoalMoney = 0
        childGoalMoney = 0
        parentGoalMoney = 0
    }
}