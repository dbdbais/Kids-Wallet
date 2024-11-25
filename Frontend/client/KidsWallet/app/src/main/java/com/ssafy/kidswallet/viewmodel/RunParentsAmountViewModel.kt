package com.ssafy.kidswallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RunParentsAmountViewModel : ViewModel() {
    private val defaultAmount = 25000
    private val _amount = MutableStateFlow(defaultAmount)
    val amount: StateFlow<Int> get() = _amount

    fun setAmount(newAmount: Int) {
        viewModelScope.launch {
            _amount.value = newAmount
        }
    }

    fun resetAmount() {
        viewModelScope.launch {
            _amount.value = defaultAmount
        }
    }
}