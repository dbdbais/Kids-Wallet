package com.ssafy.kidswallet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DdayBadge(remainingDays: Int) {
    Text(
        text = "D-$remainingDays",
        color = Color(0xFF6DCEF5),
        fontWeight = FontWeight.Bold,
        style = FontSizes.h12,
        modifier = Modifier
            .background(Color(0xFFE9F8FE), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}

@Composable
fun SuccessBadge(successOrFail: String) {
    Text(
        text = successOrFail,
        color = Color(0xFF6DCEF5),
        fontWeight = FontWeight.Bold,
        style = FontSizes.h12,
        modifier = Modifier
            .background(Color(0xFFE9F8FE), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}
