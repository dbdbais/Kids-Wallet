package com.ssafy.kidswallet.ui.screens.run.parents

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top

@Composable
fun RunParentsRegisterScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Top(title = "같이 달리기", navController = navController)

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "달리기 내용 확인",
            style = FontSizes.h32,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // 메인 콘텐츠 컨테이너
        Box(
            modifier = Modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center // Box 내부의 콘텐츠를 가운데 정렬
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth() // 컨테이너가 사용 가능한 모든 공간을 차지하도록 설정
                    .background(Color(0xFFF7F7F7), shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
                    .widthIn(max = 300.dp), // 원하는 최대 너비를 설정하여 콘텐츠를 제한
                contentAlignment = Alignment.Center // Box 내부의 콘텐츠를 가운데 정렬
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .wrapContentWidth() // 콘텐츠의 너비를 내용에 맞게 조정
                ) {
                    // 이미지
                    Image(
                        painter = painterResource(id = R.drawable.icon_bundle), // 이미지 리소스 교체 필요
                        contentDescription = "달리기 이미지",
                        modifier = Modifier.size(120.dp)
                    )

                    // 금액 텍스트
                    Text(
                        text = "25,000원 달리기",
                        color = Color(0xFF6DCEF5),
                        style = FontSizes.h24,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // 설명 텍스트
                    Text(
                        text = "코딩 공부를 위한 삼성 노트북",
                        style = FontSizes.h20,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    // 날짜 텍스트
                    Text(
                        text = "2024.10.30까지\n멤버와 함께 목표를 달성 하세요",
                        style = FontSizes.h16,
                        color = Color(0xFF8C8595),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Members list
        Column(modifier = Modifier.fillMaxWidth()) {
            MemberRow(
                name = "나",
                amount = "목표 12,500원",
                imageResId = R.drawable.character_me // Replace with your image resource
            )

            Spacer(modifier = Modifier.height(8.dp))

            MemberRow(
                name = "응애재훈",
                amount = "목표 12,500원",
                imageResId = R.drawable.character_run_member // Replace with your image resource
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // 신청 버튼
        BlueButton(
            onClick = { navController.navigate("run") },
            text = "신청하기",
            modifier = Modifier
                .width(400.dp)
                .padding(bottom = 20.dp)
        )
    }
}

@Composable
fun MemberRow(name: String, amount: String, imageResId: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "$name 이미지",
                modifier = Modifier.size(55.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = name,
                style = FontSizes.h16,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = amount,
            style = FontSizes.h16,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8C8595)
        )
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560",
    showSystemUi = true
)
@Composable
fun RunParentsRegisterScreenPreview() {
    RunParentsRegisterScreen(navController = rememberNavController())
}
