package com.ssafy.kidswallet.ui.screens.makeaccount

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.GrayButton
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.viewmodel.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun ConnectAccountScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
    val realName = storedUserData?.userRealName
    val isActive = remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(2500) // 로딩 시간 조절
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
        ) {
            Top(title = "통장 조회", navController = navController) // BackButton 사용
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .background(Color(0xFFE9F8FE)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = realName ?: "알 수 없는 사용자",
                    style = FontSizes.h32,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF6DCEF5)
                )
                Text(
                    text = "님의",
                    style = FontSizes.h24,
                    fontWeight = FontWeight.Black
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "통장이 조회됐어요",
                    style = FontSizes.h24,
                    fontWeight = FontWeight.Black,
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = painterResource(id = R.drawable.icon_bankbook),
                contentDescription = "bankbook",
                modifier = Modifier
                    .width(80.dp) // 가로 크기 조절
                    .height(80.dp) // 세로 크기 조절
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "나의 통장 목록",
                style = FontSizes.h24,
                fontWeight = FontWeight.Bold
            )

            if (isLoading) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(32.dp),
                        )
                        .background(Color(0xFFE9F8FE), shape = RoundedCornerShape(32.dp))
                        .padding(32.dp)
                        .size(200.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ImageLoadingIndicator()
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            isActive.value = !isActive.value
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ssafy),
                        contentDescription = "SSAFY Logo",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.Black)
                    )
                    Spacer(modifier = Modifier.width(8.dp))


                    Column {
                        Text(
                            text = "SSAFY 뱅크",
                            fontWeight = FontWeight.Bold,
                            style = FontSizes.h12,
                            color = Color.Gray,
                            letterSpacing = 1.5.sp
                        )
                        Text(
                            text = "991223******",
                            fontWeight = FontWeight.Bold,
                            style = FontSizes.h16,
                            letterSpacing = 1.5.sp
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(
                            id = if (isActive.value) R.drawable.icon_check_active else R.drawable.icon_check
                        ),
                        contentDescription = "Status Icon",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 54.dp, start = 16.dp, end = 16.dp) // 원하는 절대 거리 설정
        ) {
            if (isActive.value == true) {
                BlueButton(
                    onClick = {
                        navController.navigate("makeAccount")
                    },
                    text = "연동하기",
                    modifier = Modifier
                        .width(400.dp) // 원하는 너비 설정
                        .align(Alignment.BottomCenter), // 화면 하단 중앙에 정렬
                    elevation = 4
                )
            } else {
                GrayButton(
                    onClick = {

                    },
                    text = "연동하기",
                    modifier = Modifier
                        .width(400.dp) // 원하는 너비 설정
                        .align(Alignment.BottomCenter), // 화면 하단 중앙에 정렬
                    elevation = 4
                )
            }
        }
    }
}

@Composable
fun ImageLoadingIndicator() {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier
            .size(100.dp)
            .graphicsLayer {
                rotationZ = rotation
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.loading), // 'loading.png' 리소스 사용
            contentDescription = "로딩 중",
            modifier = Modifier.size(100.dp) // 이미지 크기 조절
        )
    }
}
