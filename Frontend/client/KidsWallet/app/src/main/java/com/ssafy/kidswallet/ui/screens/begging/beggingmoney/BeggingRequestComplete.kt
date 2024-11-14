package com.ssafy.kidswallet.ui.screens.begging.beggingmoney

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.LightGrayButton
import com.ssafy.kidswallet.ui.screens.begging.beggingmoney.BeggingRequestReasonScreen

@Composable
fun BeggingRequestCompleteScreen(
    navController: NavController,
    name: String?,
    amount: Int,
    reason: String?
) {
    Column(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                        .alpha(0f)
                )
                Text(
                    text = "조르기",
                    style = FontSizes.h20,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .background(Color(0xFFE9F8FE)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name ?: "알 수 없음",
                    style = FontSizes.h32,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF6DCEF5)
                )
                Text(
                    text = "님에게",
                    style = FontSizes.h24,
                    fontWeight = FontWeight.Black
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$amount",
                    style = FontSizes.h32,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF6DCEF5)
                )
                Text(
                    text = "원을 요청했어요",
                    style = FontSizes.h24,
                    fontWeight = FontWeight.Black
                )

            }
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = painterResource(id = R.drawable.logo_coin),
                contentDescription = "Coin with Amount",
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .border(3.dp, Color(0xFFB2EBF2), RoundedCornerShape(16.dp))
                    .border(6.dp, Color(0xFF99DDF8).copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                    .border(9.dp, Color(0xFF99DDF8).copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(top = 16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.character_old_girl),
                        contentDescription = "Female Character",
                        modifier = Modifier
                            .size(160.dp)
                    )
                    Text(
                        text = reason ?: "알 수 없음",
                        style = FontSizes.h20,
                        fontWeight = FontWeight.Black
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$amount",
                            style = FontSizes.h24,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF6DCEF5)
                        )
                        Text(
                            text = "원이 필요해요!",
                            style = FontSizes.h20,
                            fontWeight = FontWeight.Black
                        )

                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 54.dp) // 원하는 절대 거리 설정
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                LightGrayButton(
                    onClick = {
                        navController.navigate("mainPage") {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    text = "홈으로 가기",
                    modifier = Modifier
                        .width(130.dp), // 원하는 너비 설정
                    height = 50,
                    elevation = 4
                )
                BlueButton(
                    onClick = {
                        navController.navigate("begging") {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    text = "조르기로 가기",
                    modifier = Modifier
                        .width(230.dp), // 원하는 너비 설정
                    height = 50,
                    elevation = 4
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560", // Galaxy S24 Ultra 해상도에 맞추기
    showSystemUi = true
)
@Composable
fun BeggingRequestCompleteScreenPreview() {
    // 임시 NavController를 생성하여 프리뷰에서 사용
    val navController = rememberNavController()
    BeggingRequestReasonScreen(navController = navController, name = "테스트 사용자", amount = 50000) // 테스트용 이름 전달
}