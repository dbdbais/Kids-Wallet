package com.ssafy.kidswallet.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kidswallet.data.model.PersonModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PersonViewModel : ViewModel() {
    // 데이터를 저장할 상태 리스트
    private val _peopleList = MutableStateFlow<List<PersonModel>>(emptyList())
    val peopleList: StateFlow<List<PersonModel>> = _peopleList

    private val _selectedPerson = MutableStateFlow<PersonModel?>(null)
    val selectedPerson: StateFlow<PersonModel?> = _selectedPerson

    fun setSelectedPerson(person: PersonModel) {
        _selectedPerson.value = person
    }

    fun resetSelectedPerson() {
        _selectedPerson.value = null
    }

    fun fetchPeople() {
        loadPeople()
    }

    private fun loadPeople() {
        viewModelScope.launch {
            // 예시: 직접 데이터 생성
            val people = listOf(
                PersonModel(id = 1, name = "John Dou", gender = "남"),
                PersonModel(id = 2, name = "Sarah Corner", gender = "여"),
            )
            _peopleList.value = people
        }
    }
}
