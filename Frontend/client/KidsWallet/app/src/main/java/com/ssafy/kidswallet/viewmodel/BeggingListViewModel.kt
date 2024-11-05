package com.ssafy.kidswallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.RecentlyBeggingModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BeggingListViewModel : ViewModel() {
    private val _recentlyBeggingList = MutableStateFlow<List<RecentlyBeggingModel>>(emptyList())
    val recentlyBeggingModel: StateFlow<List<RecentlyBeggingModel>> = _recentlyBeggingList

    fun fetchBeggingList() {
        loadBeggingList()
    }

    private fun loadBeggingList() {
        viewModelScope.launch {
            // 예시: 직접 데이터 생성
            val beggingList = listOf(
                RecentlyBeggingModel(date = "2024-03-02", name = "John Dou", money = "13,000"),
                RecentlyBeggingModel(date = "2024-03-02", name = "John Dou", money = "13,000"),
                RecentlyBeggingModel(date = "2024-03-02", name = "John Dou", money = "13,000"),
                RecentlyBeggingModel(date = "2024-03-02", name = "John Dou", money = "13,000"),
                RecentlyBeggingModel(date = "2024-03-02", name = "John Dou", money = "13,000"),
                RecentlyBeggingModel(date = "2024-03-02", name = "John Dou", money = "13,000"),
            )
            _recentlyBeggingList.value = beggingList
        }
    }

}