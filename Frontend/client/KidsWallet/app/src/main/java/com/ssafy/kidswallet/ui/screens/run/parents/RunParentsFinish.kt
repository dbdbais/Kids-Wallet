package com.ssafy.kidswallet.ui.screens.run.parents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BottomNavigationBar
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.ui.components.GoldenRatioUtils
import com.ssafy.kidswallet.ui.components.SuccessBadge

@Composable
fun RunParentsFinishScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Top(title = "같이 달리기", navController = navController)
            }

            // Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 같이 달리기와 함께 달리기를 하나의 Row로 묶음
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // 버튼 사이 간격 조정
                ) {
                    Button(
                        onClick = { /* 같이 달리기 클릭 시 동작 */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9D9D9)),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        modifier = Modifier.height(32.dp) // 버튼 높이 조정
                    ) {
                        Text(
                            text = "같이 달리기",
                            color = Color(0xFF8C8595),
                            style = FontSizes.h16,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = { /* 함께 달리기 클릭 시 동작 */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7F7F7)),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        modifier = Modifier.height(32.dp) // 버튼 높이 조정
                    ) {
                        Text(
                            text = "함께 달리기",
                            color = Color.Gray,
                            style = FontSizes.h16,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // 지난 달리기 텍스트
                Text(
                    text = "진행중인 달리기",
                    fontWeight = FontWeight.Bold,
                    style = FontSizes.h16,
                    color = Color(0xFF6DCEF5),
                    modifier = Modifier
                        .clickable { navController.navigate("run") }
                        .align(Alignment.CenterVertically)
                )
            }

            // 그리드 레이아웃으로 카드 배치
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 56.dp),
                contentPadding = PaddingValues(
                    start = 8.dp,
                    top = 8.dp,
                    end = 8.dp,
                    bottom = 16.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 반복되는 카드들
                items(4) { index ->
                    Box(
                        modifier = Modifier
                            .width(150.dp)
                            .height(GoldenRatioUtils.goldenHeight(150f).dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(16.dp),
                            )
                            .background(Color(0xFF6DCEF5))
                            .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
                            .padding(16.dp)
                            .clickable {
                                navController.navigate("runParentsFinishDetail")
                            },
                        contentAlignment = Alignment.TopStart // 텍스트는 상단 좌측에 정렬
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Top
                        ) {
                            // 상단 D-7 배지
                            SuccessBadge(successOrFail = "성공")

                            Spacer(modifier = Modifier.height(8.dp))

                            // 카드 텍스트
                            Text(
                                text = "코딩 공부를 위한\n삼성 노트북 ${index + 1}",
                                style = FontSizes.h16,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start,
                                color = Color(0xFFFFFFFF)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // 지금까지 모은 금액 (포맷팅 적용)
                            Text(
                                text = "${NumberUtils.formatNumberWithCommas(500000)}원",
                                style = FontSizes.h20,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start,
                                color = Color(0xFFFFFFFF)
                            )
                        }

                        // 이미지 (우측 하단에 고정)
                        Image(
                            painter = painterResource(id = R.drawable.icon_bundle),
                            contentDescription = "삼성 노트북 이미지 $index",
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.BottomEnd) // 박스의 우측 하단에 배치
                                .offset(x = (16).dp, y = (12).dp)
                                .rotate(20f)
                        )
                    }
                }
            }
        }

        // Bottom navigation
        BottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter), navController
        )
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560",
    showSystemUi = true
)
@Composable
fun RunParentsFinishScreenPreview() {
    RunParentsFinishScreen(navController = rememberNavController())
}