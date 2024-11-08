package com.ssafy.kidswallet.ui.components

import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {
    fun formatDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            outputFormat.format(date ?: "")
        } catch (e: Exception) {
            dateString
        }
    }
}

//val originalDate = "2024-11-06T11:37:34.987481"
//DateUtils의 formatDate 함수를 사용하여 날짜 형식을 변환
//val formattedDate = DateUtils.formatDate(originalDate)