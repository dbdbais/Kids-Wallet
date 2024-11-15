package com.ssafy.kidswallet.ui.screens.run

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BottomNavigationBar
import com.ssafy.kidswallet.ui.components.DdayBadge
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.ui.components.GoldenRatioUtils
import com.ssafy.kidswallet.viewmodel.LoginViewModel
import com.ssafy.kidswallet.viewmodel.TogetherListViewModel

@Composable
fun RunScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    togetherListViewModel: TogetherListViewModel = viewModel()
) {
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
    val togetherList = togetherListViewModel.togetherList.collectAsState().value

    // API 호출을 통해 데이터 로드
    storedUserData?.userId?.let { userId ->
        togetherListViewModel.fetchTogetherList(userId)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Top(title = "행복 달리기", navController = navController)
            }

            // Tabs
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
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
                    text = "지난 달리기",
                    fontWeight = FontWeight.Bold,
                    style = FontSizes.h16,
                    color = Color(0xFF6DCEF5),
                    modifier = Modifier
                        .clickable { navController.navigate("runParentsFinish") }
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
                // 첫 번째 고정 카드
                item {
                    Box(
                        modifier = Modifier
                            .width(150.dp)
                            .height(GoldenRatioUtils.goldenHeight(150f).dp) // Golden Ratio 적용
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
                            .padding(16.dp)
                            .clickable {
                                navController.navigate("runParents")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "새로운 달리기\n시작하기",
                                style = FontSizes.h16,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Image(
                                painter = painterResource(id = R.drawable.icon_plus_circle),
                                contentDescription = "Add Run",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }

                // TogetherList API에서 받아온 데이터 카드
                items(togetherList.size) { index ->
                    val item = togetherList[index]
                    Box(
                        modifier = Modifier
                            .width(150.dp)
                            .height(GoldenRatioUtils.goldenHeight(150f).dp) // Golden Ratio 적용
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
                            .background(Color(0xFF6DCEF5))
                            .border(1.dp, Color(0xFF6DCEF5), RoundedCornerShape(16.dp))
                            .padding(16.dp)
                            .clickable {
                                navController.navigate("runParentsDetail")
                            },
                        contentAlignment = Alignment.TopStart
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Top
                        ) {
                            // D-day 배지
                            DdayBadge(remainingDays = item.dDay)

                            Spacer(modifier = Modifier.height(8.dp))

                            // 카드 텍스트
                            Text(
                                text = item.targetTitle,
                                style = FontSizes.h16,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // 지금까지 모은 금액
                            Text(
                                text = "${NumberUtils.formatNumberWithCommas(item.currentAmount)}원",
                                style = FontSizes.h20,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start,
                                color = Color.White
                            )
                        }

                        // 이미지 (우측 하단에 고정)
                        Image(
                            painter = painterResource(id = R.drawable.icon_bundle),
                            contentDescription = "Run Item Image",
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.BottomEnd)
                                .offset(x = 16.dp, y = 12.dp)
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
