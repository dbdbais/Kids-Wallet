package com.ssafy.kidswallet.viewmodel.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class StateBeggingMissionViewModel : ViewModel() {
    var imageText by mutableStateOf("")
        private set

    fun setBase64Text(text: String) {
        imageText = text
    }
}