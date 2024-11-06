package com.ssafy.kidswallet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BlueButton(
    onClick: () -> Unit,
    text: String,
    height: Int = 50, // 기본 버튼 높이를 50dp로 설정
    elevation: Int = 8 // 기본 그림자 높이를 8dp로 설정
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation.dp,
                shape = RoundedCornerShape(15.dp)
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF99DDF8), Color(0xFF6DCEF5))
                ),
                shape = RoundedCornerShape(15.dp)
            )
            .clickable(onClick = onClick)
            .height(height.dp), // 높이 설정
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

