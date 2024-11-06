package com.ssafy.kidswallet.ui.screens.run.parents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top

@Composable
fun RunParentsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Top(title = "같이 달리기", navController = navController)
        Text(
            text = "달리기 목표 세우기",
            style = FontSizes.h24,
            fontWeight = FontWeight.Bold
        )

        // 목표 입력 및 날짜 설정
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF99DDF8), Color(0xFF6DCEF5)), // 시작과 끝 색상
                        start = Offset(0f, Float.POSITIVE_INFINITY), // 좌하단
                        end = Offset(Float.POSITIVE_INFINITY, 0f) // 우상단
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "목표를 세워봐요", style = FontSizes.h16)
            Text(text = "2024.10.30 까지 같이 달리기", style = FontSizes.h16)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 사진 첨부 박스
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF7F7F7), RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "사진을 첨부할 수 있어요",
                style = FontSizes.h16,
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.icon_plus2),
                contentDescription = "사진 첨부",
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 다음 버튼
        Button(
            onClick = { /* 다음 동작 구현 */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFF6DCEF5), RoundedCornerShape(16.dp)),
            contentPadding = PaddingValues()
        ) {
            Text(text = "다음", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560",
    showSystemUi = true
)
@Composable
fun RunParentsScreenPreview() {
    RunParentsScreen(navController = rememberNavController())
}