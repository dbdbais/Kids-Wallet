package com.ssafy.kidswallet.ui.screens.run

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.BottomNavigationBar
import com.ssafy.kidswallet.ui.components.DdayBadge
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.ui.components.GoldenRatioUtils
import com.ssafy.kidswallet.ui.components.TopToMain
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

    // Dialog 상태 관리
    val showAlertDialog = remember { mutableStateOf(false) }

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
                TopToMain(title = "같이 달리기", navController = navController)
            }

            // Tabs
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
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
                    text = "지난 달리기",
                    fontWeight = FontWeight.Bold,
                    style = FontSizes.h16,
                    color = Color(0xFF6DCEF5),
                    modifier = Modifier
                        .clickable { navController.navigate("runParentsFinish") }
                        .align(Alignment.CenterVertically)
                )
            }

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
                if (storedUserData?.userRole == "CHILD") {
                    item {
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .height(GoldenRatioUtils.goldenHeight(150f).dp)
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
                }


                items(togetherList.size) { index ->
                    val item = togetherList[index]
                    Box(
                        modifier = Modifier
                            .width(150.dp)
                            .height(GoldenRatioUtils.goldenHeight(150f).dp)
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
                            .background(Color(0xFF6DCEF5))
                            .border(1.dp, Color(0xFF6DCEF5), RoundedCornerShape(16.dp))
                            .padding(16.dp)
                            .clickable {
                                val route = when (storedUserData?.userRole) {
                                    "CHILD" -> {
                                        if (item.isAccept) {
                                            "runParentsDetail/${item.togetherRunId}"
                                        } else {
                                            showAlertDialog.value = true
                                            null
                                        }
                                    }
                                    "PARENT" -> "parentsDetail/${item.togetherRunId}" // isAccept와 상관없이 라우팅
                                    else -> null // 다른 경우를 대비한 처리
                                }

                                route?.let { navController.navigate(it) }
                            },
                        contentAlignment = Alignment.TopStart
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Top
                        ) {
                            DdayBadge(remainingDays = item.dDay)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = item.targetTitle,
                                style = FontSizes.h16,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "${NumberUtils.formatNumberWithCommas(item.targetAmount)}원",
                                style = FontSizes.h20,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start,
                                color = Color.White
                            )
                        }

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

        BottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter), navController
        )
    }

    if (storedUserData?.userRole == "CHILD" && showAlertDialog.value) {
        AlertDialog(
            onDismissRequest = { showAlertDialog.value = false },
            title = {
                Text(
                    text = "알림",
                    color = Color(0xFFFBC02D),
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "승인 대기 중입니다.",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8C8595)
                )
            },
            confirmButton = {
                BlueButton(
                    onClick = { showAlertDialog.value = false },
                    text = "확인",
                    modifier = Modifier.width(260.dp),
                    height = 40,
                    elevation = 0
                )
            }
        )
    }
}

