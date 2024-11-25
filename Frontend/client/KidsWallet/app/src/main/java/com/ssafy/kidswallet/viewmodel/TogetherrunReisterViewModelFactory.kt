package com.ssafy.kidswallet.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssafy.kidswallet.data.network.ApiService

// 팩토리 클래스 정의
class TogetherrunReisterViewModelFactory(
    private val apiService: ApiService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TogetherrunReisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TogetherrunReisterViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
