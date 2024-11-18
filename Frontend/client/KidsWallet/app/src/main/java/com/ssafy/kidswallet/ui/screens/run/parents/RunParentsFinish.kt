package com.ssafy.kidswallet.ui.screens.run.parents

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
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.ui.components.GoldenRatioUtils
import com.ssafy.kidswallet.ui.components.SuccessBadge
import com.ssafy.kidswallet.viewmodel.LoginViewModel
import com.ssafy.kidswallet.viewmodel.TogetherCompleteListViewModel

@Composable
fun RunParentsFinishScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    togetherCompleteListViewModel: TogetherCompleteListViewModel = viewModel()
) {
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
    val togetherCompleteList = togetherCompleteListViewModel.togetherCompleteList.collectAsState().value

    // API 호출을 통해 데이터 로드
    storedUserData?.userId?.let { userId ->
        togetherCompleteListViewModel.fetchTogetherCompleteList(userId)
    }

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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { /* 같이 달리기 클릭 시 동작 */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9D9D9)),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(
                            text = "같이 달리기",
                            color = Color(0xFF8C8595),
                            style = FontSizes.h16,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = { navController.navigate("runOthers") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7F7F7)),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(
                            text = "함께 달리기",
                            color = Color.Gray,
                            style = FontSizes.h16,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

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

            // Check if the list is empty and display an image if true
            if (togetherCompleteList.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(50.dp))
                    Image(
                        painter = painterResource(id = R.drawable.icon_no_transaction), // Replace with your desired image resource
                        contentDescription = "No Data",
                        modifier = Modifier.size(150.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "지난 달리기 기록이 없습니다.",
                        style = FontSizes.h20,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
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
                    items(togetherCompleteList.size) { index ->
                        val item = togetherCompleteList[index]
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .height(GoldenRatioUtils.goldenHeight(150f).dp)
                                .shadow(
                                    elevation = 2.dp,
                                    shape = RoundedCornerShape(16.dp),
                                )
                                .background(Color(0xFF6DCEF5))
                                .border(1.dp, Color(0xFF6DCEF5), RoundedCornerShape(16.dp))
                                .padding(16.dp)
                                .clickable {
                                    navController.navigate("runParentsFinishDetail/${item.togetherRunId}")
                                },
                            contentAlignment = Alignment.TopStart
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Top
                            ) {
                                // 성공 여부 배지
                                SuccessBadge(successOrFail = if (item.isAccept) "성공" else "실패")

                                Spacer(modifier = Modifier.height(8.dp))

                                // 목표 제목
                                Text(
                                    text = item.targetTitle,
                                    style = FontSizes.h16,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start,
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // 목표 금액
                                Text(
                                    text = "${NumberUtils.formatNumberWithCommas(item.targetAmount)}원",
                                    style = FontSizes.h20,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start,
                                    color = Color.White
                                )
                            }

                            // 이미지
                            Image(
                                painter = painterResource(id = R.drawable.icon_bundle),
                                contentDescription = "목표 이미지 $index",
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
        }

        // Bottom navigation
        BottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter), navController
        )
    }
}

