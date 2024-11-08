package com.ssafy.kidswallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class RunMemberViewModel : ViewModel() {
    var selectedMember by mutableStateOf<String?>(null)
        private set

    var members = listOf(
        "응애재훈1" to "ABC123",
        "응애성혁1" to "DEF456",
        "응애준수1" to "GHI789",
        "응애강우1" to "JKL012",
        "응애범준1" to "MNO345",
        "응애재훈2" to "ABC123",
        "응애성혁2" to "DEF456",
        "응애준수2" to "GHI789",
        "응애강우2" to "JKL012",
        "응애범준3" to "MNO345",
        "응애재훈3" to "ABC123",
        "응애성혁4" to "DEF456",
        "응애준수4" to "GHI789",
        "응애강우4" to "JKL012",
        "응애범준4" to "MNO345",
        "응애재훈5" to "ABC123",
        "응애성혁5" to "DEF456",
        "응애준수5" to "GHI789",
        "응애강우5" to "JKL012",
        "beomjun" to "MNO345"
    )

    fun toggleMemberSelection(member: String) {
        selectedMember = if (selectedMember == member) {
            null // 선택 해제
        } else {
            member // 새로운 선택
        }
    }
}
