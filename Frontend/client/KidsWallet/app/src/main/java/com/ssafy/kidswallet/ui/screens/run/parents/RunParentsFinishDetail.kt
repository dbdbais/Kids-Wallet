package com.ssafy.kidswallet.ui.screens.run.parents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.DdayBadge
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.LightGrayButton
import com.ssafy.kidswallet.ui.components.SuccessBadge
import com.ssafy.kidswallet.ui.components.Top

@Composable
fun RunParentsFinishDetailScreen(navController: NavController) {
    val collectedAmount = 25000 // You can change this value dynamically as needed
    val formattedAmount = NumberUtils.formatNumberWithCommas(collectedAmount)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Top(
            title = "같이 달리기",
            navController = navController
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF6DCEF5)) // 배경색
                .padding(16.dp)
                .padding(top = 30.dp, bottom = 14.dp), // 상하 패딩을 추가하여 크기를 늘림
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 목표 이미지 및 정보 박스
                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(45.dp)
                        )
                        .background(Color(0xFF99DDF8), RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    // D-Day 배지를 좌측 상단에 위치시킴
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart) // 좌측 상단에 정렬
                            .padding(8.dp) // 추가 패딩 적용 (필요 시)
                    ) {
                        SuccessBadge(successOrFail = "성공")
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // 목표 이미지와 금액 정보
                        Image(
                            painter = painterResource(id = R.drawable.icon_labtop),
                            contentDescription = "목표 이미지",
                            modifier = Modifier
                                .size(250.dp)
                        )

                        Text(
                            text = formattedAmount + "원을 모았어요!",
                            style = FontSizes.h20,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // 목표 금액과 마감 기한 텍스트
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "2024.10.30 까지",
                        style = FontSizes.h16,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "목표 25,000원",
                        style = FontSizes.h16,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 참여자 정보
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ParticipantInfo(
                name = "나",
                currentAmount = 0,
                goalAmount = 12500,
                imageResId = R.drawable.character_me
            )

            Spacer(modifier = Modifier.height(8.dp))

            ParticipantInfo(
                name = "응애재훈",
                currentAmount = 0,
                goalAmount = 12500,
                imageResId = R.drawable.character_run_member
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        BlueButton(
            onClick = {
                navController.navigate("runParentsFinish")
            },
            text = "뒤로가기",
            modifier = Modifier
                .width(400.dp)
                .padding(start = 16.dp, end = 16.dp)
                .padding(bottom = 20.dp)
        )
    }
}


@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560",
    showSystemUi = true
)
@Composable
fun RunParentsFinishDetailScreenPreview() {
    RunParentsFinishDetailScreen(navController = rememberNavController())
}