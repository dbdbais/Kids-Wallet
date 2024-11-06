package com.ssafy.kidswallet.ui.screens.run.parents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top

@Composable
fun RunParentsMoneyScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Top(title = "같이 달리기", navController = navController)

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "목표 금액을 입력해 주세요",
            style = FontSizes.h24,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(50.dp))

        // 목표 입력 및 날짜 설정
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF99DDF8), Color(0xFF6DCEF5))
                    ),
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // 흰색 배경의 Row 컨테이너
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp) // 내부 패딩
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween, // 텍스트를 양 끝에 배치
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "목표를 세워봐요",
                        style = FontSizes.h16,
                        color = Color(0xFF8C8595),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "0/20",
                        style = FontSizes.h16,
                        color = Color(0xFF8C8595),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 날짜 정보 Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_callendar),
                    contentDescription = "달력",
                    modifier = Modifier.size(25.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "2024.10.30",
                    style = FontSizes.h16,
                    color = Color(0xFFFFFFFF),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "까지 같이 달리기",
                    style = FontSizes.h16,
                    color = Color(0xFF5EA0BB),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        // 사진 첨부 박스
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
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
                modifier = Modifier.size(25.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // 다음 버튼
        BlueButton(
            onClick = { navController.navigate("mainPage") },
            text = "다음",
            modifier = Modifier.width(400.dp).padding(bottom = 20.dp)
        )
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560",
    showSystemUi = true
)
@Composable
fun RunParentsMoneyScreenPreview() {
    RunParentsScreen(navController = rememberNavController())
}
