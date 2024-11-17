package com.ssafy.kidswallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class RunMemberViewModel : ViewModel() {
    var selectedMember by mutableStateOf<String?>(null)
        private set

    // 멤버 선택 토글
    fun toggleMemberSelection(member: String) {
        selectedMember = if (selectedMember == member) {
            null // 선택 해제
        } else {
            member // 새로운 선택
        }
    }
}
