package com.ssafy.kidswallet.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.TogetherrunReisterModel
import com.ssafy.kidswallet.data.network.ApiService
import kotlinx.coroutines.launch

class TogetherrunReisterViewModel(
    private val apiService: ApiService
) : ViewModel() {

    fun registerTogetherrun(
        requestModel: TogetherrunReisterModel,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = apiService.registerTogetherrun(requestModel)

                onResult(response.isSuccessful)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(false)
            }
        }
    }
}
