// RunMemberViewModel.kt
package com.ssafy.kidswallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class RunMemberViewModel : ViewModel() {
    // 기존 selectedMember 외에 추가적인 상태로 userRealName 관리
    var selectedMember by mutableStateOf<String?>(null)
        private set

    var selectedUserRealName by mutableStateOf<String?>(null)
        private set

    var selectedUserId by mutableStateOf<Int?>(null)
        private set

    // 멤버 선택 토글
    fun toggleMemberSelection(member: String, realName: String, userId: Int) {
        if (selectedMember == member) {
            selectedMember = null
            selectedUserRealName = null
            selectedUserId = null
        } else {
            selectedMember = member
            selectedUserRealName = realName
            selectedUserId = userId
        }
    }
}
