package com.ssafy.kidswallet.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ssafy.kidswallet.data.model.LoginModel
import com.ssafy.kidswallet.data.model.Relation
import com.ssafy.kidswallet.data.model.UserDataModel
import com.ssafy.kidswallet.data.network.RetrofitClient
import com.ssafy.kidswallet.dataStore
import com.ssafy.kidswallet.fcm.repository.FcmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject


// DataStore를 위한 Context 확장 함수
private val Context.dataStore by preferencesDataStore(name = "user_data")

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _loginState = MutableStateFlow<Boolean?>(null)
    val loginState: StateFlow<Boolean?> = _loginState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    private val _userData = MutableStateFlow<UserDataModel?>(null)
    val userData: StateFlow<UserDataModel?> = _userData


    private val USER_ID_KEY = intPreferencesKey("user_id")
    private val USER_NAME_KEY = stringPreferencesKey("user_name")
    private val USER_GENDER_KEY = stringPreferencesKey("user_gender")
    private val USER_REAL_NAME_KEY = stringPreferencesKey("user_real_name")
    private val USER_MONEY_KEY = intPreferencesKey("user_money")
    private val USER_ROLE_KEY = stringPreferencesKey("user_role")
    private val USER_BIRTH_KEY = stringPreferencesKey("user_birth")
    private val HAS_CARD_KEY = booleanPreferencesKey("has_card")
    private val REPRESENT_ACCOUNT_ID_KEY = stringPreferencesKey("represent_account_id")
    private val RELATIONS_KEY = stringPreferencesKey("relations")

    private val gson = Gson()

    private suspend fun saveUserDataToDataStore(userData: UserDataModel) {
        getApplication<Application>().applicationContext.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userData.userId
            preferences[USER_NAME_KEY] = userData.userName ?: ""
            preferences[USER_GENDER_KEY] = userData.userGender ?: ""
            preferences[USER_REAL_NAME_KEY] = userData.userRealName ?: ""
            preferences[USER_MONEY_KEY] = userData.userMoney
            preferences[USER_ROLE_KEY] = userData.userRole ?: ""
            preferences[USER_BIRTH_KEY] = userData.userBirth?.joinToString(",") ?: ""
            preferences[HAS_CARD_KEY] = userData.hasCard
            if (userData.representAccountId != null) {
                preferences[REPRESENT_ACCOUNT_ID_KEY] = userData.representAccountId
            } else {
                preferences.remove(REPRESENT_ACCOUNT_ID_KEY)
            }
            preferences[RELATIONS_KEY] = gson.toJson(userData.relations)
        }
    }

    private fun getRelationsFromString(json: String?): List<Relation>? {
        return if (json.isNullOrEmpty()) {
            null
        } else {
            val type = object : TypeToken<List<Relation>>() {}.type
            gson.fromJson(json, type)
        }
    }

    fun saveUserData(userData: UserDataModel) {
        viewModelScope.launch {
            saveUserDataToDataStore(userData)
        }
    }

    val fcmTokenKey = stringPreferencesKey("fcm_token")
    suspend fun getFcmToken(): String? {
        // DataStore에서 fcm_token 값을 동기적으로 가져가기
        val buffer = getApplication<Application>().applicationContext.dataStore.data.first()
        return  buffer[fcmTokenKey]
    }
    fun getStoredUserData(): StateFlow<UserDataModel?> {
        val dataFlow = getApplication<Application>().applicationContext.dataStore.data
            .map { preferences ->
                UserDataModel(
                    userId = preferences[USER_ID_KEY] ?: 0,
                    userName = preferences[USER_NAME_KEY] ?: "",
                    userGender = preferences[USER_GENDER_KEY] ?: "",
                    userRealName = preferences[USER_REAL_NAME_KEY] ?: "",
                    userMoney = preferences[USER_MONEY_KEY] ?: 0,
                    userRole = preferences[USER_ROLE_KEY] ?: "",
                    userBirth = preferences[USER_BIRTH_KEY]?.split(",")?.map { it.toInt() },
                    hasCard = preferences[HAS_CARD_KEY] ?: false,
                    representAccountId = preferences[REPRESENT_ACCOUNT_ID_KEY],
                    relations = preferences[RELATIONS_KEY]?.let {
                        gson.fromJson(it, object : TypeToken<List<Relation>>() {}.type)
                    }
                )
            }
        val userDataStateFlow = MutableStateFlow<UserDataModel?>(null)
        viewModelScope.launch {
            val userData = dataFlow.first()
            userDataStateFlow.value = userData
        }
        return userDataStateFlow
    }

    fun loginUser(userName: String, userPassword: String) {
        val loginModel = LoginModel(userName, userPassword)

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.loginUser(loginModel)

                if (response.isSuccessful) {
                    _loginState.value = true

                    val apiResponse = response.body()
                    val userData = apiResponse?.data
                    if (userData != null) {
                        _userData.value = userData
                        saveUserData(userData)
                    }

                    //fcm
                    //TODO: userData에서 token을 받아 레트로핏으로 save 요청
                    Log.d("getFcmToken","FcmToken: "+getFcmToken())
                    val userId: Long? = apiResponse?.data?.userId?.toLong()
                    val fcmRepository = FcmRepository()
                    fcmRepository.sendTokenToServer(userId,getFcmToken());


                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody?.let {
                        try {
                            val jsonObject = JSONObject(it)
                            jsonObject.getString("message")
                        } catch (e: Exception) {
                            "An error occurred"
                        }
                    } ?: "An error occurred"

                    _errorState.value = "로그인 실패: $errorMessage"
                    _loginState.value = false
                }
            } catch (e: Exception) {
                _errorState.value = "Network error: ${e.message}"
                _loginState.value = false
            }
        }
    }
}