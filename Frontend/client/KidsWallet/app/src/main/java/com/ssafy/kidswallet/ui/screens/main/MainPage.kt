package com.ssafy.kidswallet.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.kidswallet.ui.components.Top

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BottomNavigationBar

@Composable
fun MainPageScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 배경 이미지
        Image(
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = "Main Background",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .offset(y = (-200).dp)
        )

        // 콘텐츠 레이아웃
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 코인 이미지와 금액 텍스트가 포함된 이미지
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 16.dp), // 아래쪽 여백 추가
                horizontalArrangement = Arrangement.Start, // Row 내부 요소를 왼쪽으로 정렬
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_coin),
                    contentDescription = "Coin with Amount",
                    modifier = Modifier
                        .width(60.dp) // 가로 크기 조절
                        .height(50.dp) // 세로 크기 조절
                )
                Text(
                    text = "500,000",
                    fontWeight = FontWeight.W900,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // 안내 문구 이미지 (왼쪽 정렬)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 16.dp), // 아래쪽 여백 추가
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_add),
                    contentDescription = "Waiting Message",
                    modifier = Modifier
                        .size(80.dp) // 이미지 크기 조절 (80 x 80)
                )
                Text(
                    text = "아이를 추가해주세요",
                    fontWeight = FontWeight.W900,
                    fontSize = 24.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // 네 개의 아이콘 박스 (2x2 Grid)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CardApplicationBox(navController)
                BeggingApplicationBox(navController)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TogetherApplicationBox(navController)
                QuizApplicationBox(navController)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // 메모장 이미지
            Image(
                painter = painterResource(id = R.drawable.memo), // 메모장 이미지
                contentDescription = "Memo Pad",
                modifier = Modifier
                    .padding(16.dp)
                    .height(250.dp)
                    .width(450.dp)
            )
        }
        BottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun CardApplicationBox(navController: NavController) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .clickable { navController.navigate("myWallet") }
    ) {
        Image(
            painter = painterResource(id = R.drawable.blue_box),
            contentDescription = "Background Box",
            modifier = Modifier
                .fillMaxSize()
        )

        Text(
            text = "내 지갑",
            fontSize = 24.sp,
            fontWeight = FontWeight.W900,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 40.dp, top = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.icon_wallet),
            contentDescription = "Wallet Icon",
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-20).dp)
                .offset(y = (-10).dp)
        )
    }
}

@Composable
fun BeggingApplicationBox(navController: NavController) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .clickable { navController.navigate("begging") }
    ) {
        Image(
            painter = painterResource(id = R.drawable.pink_box),
            contentDescription = "Background Box",
            modifier = Modifier
                .fillMaxSize()
        )

        Text(
            text = "용돈 조르기",
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 32.dp, top = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.logo_together),
            contentDescription = "Together Icon",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-30).dp)
                .offset(y = (-10).dp)
        )
    }
}

@Composable
fun TogetherApplicationBox(navController: NavController) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .clickable { navController.navigate("runParents") }
    ) {

        Image(
            painter = painterResource(id = R.drawable.yellow_box),
            contentDescription = "Background Box",
            modifier = Modifier
                .fillMaxSize()
        )

        Text(
            text = "행복 달리기",
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 40.dp, top = 16.dp)
        )


        Image(
            painter = painterResource(id = R.drawable.logo_together2),
            contentDescription = "Together2 Icon",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-30).dp)
                .offset(y = (-10).dp)
        )
    }
}

@Composable
fun QuizApplicationBox(navController: NavController) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .clickable { navController.navigate("quiz") }
    ) {

        Image(
            painter = painterResource(id = R.drawable.green_box),
            contentDescription = "Background Box",
            modifier = Modifier
                .fillMaxSize()
        )

        Text(
            text = "퀴즈",
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 40.dp, top = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.logo_quiz),
            contentDescription = "Quiz Icon",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-20).dp)
                .offset(y = (-20).dp)
        )
    }
}
