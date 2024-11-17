package com.ssafy.kidswallet.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.MessageModel
import com.ssafy.kidswallet.data.network.RetrofitClient.apiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoticeListViewModel : ViewModel() {

    private val _noticeMessage = MutableStateFlow<List<MessageModel>>(emptyList())
    val noticeMessage: StateFlow<List<MessageModel>> = _noticeMessage

    fun fetchNotice(userId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.noticeList(userId)
                if (response.isSuccessful) {
                    val messages = response.body() ?: emptyList()
                    _noticeMessage.value = messages
                    Log.d("NoticeListViewModel", "Fetched notice messages: $messages")
                } else {
                    Log.e("NoticeListViewModel", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("NoticeListViewModel", "Network error: ${e.message}")
            }
        }
    }
}