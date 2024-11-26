package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.network.RetrofitClient.apiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeleteNoticeViewModel : ViewModel() {

    private val _isNoticeDeleted = MutableStateFlow(false)
    val isNoticeDeleted: StateFlow<Boolean> = _isNoticeDeleted

    fun deleteNotice(userId: String, noticeNum: String) {
        viewModelScope.launch {
            try {
                val response = apiService.deleteNotice(userId, noticeNum)
                if (response.isSuccessful) {
                    _isNoticeDeleted.value = true
                } else {
                    _isNoticeDeleted.value = false
                    Log.e("DeleteNoticeViewModel", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                _isNoticeDeleted.value = false
                Log.e("DeleteNoticeViewModel", "Network error: ${e.message}")
            }
        }
    }
}