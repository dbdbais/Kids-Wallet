package com.ssafy.kidswallet.ui.screens.begging

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BottomNavigationBar
import com.ssafy.kidswallet.ui.components.Top

@Composable
fun BeggingScreen(navController: NavController) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val boxHeight = screenHeight * 0.35f

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Top(title = "조르기", navController = navController)
            Spacer(modifier = Modifier.height(32.dp))
            BeggingMoney(navController, boxHeight)
            Spacer(modifier = Modifier.height(48.dp))
            BeggingMission(navController, boxHeight)
        }
        BottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter), navController
        )
    }
}

@Composable
fun BeggingMoney(navController: NavController, boxHeight: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(boxHeight)
            .shadow(
                elevation = 12.dp, // 그림자 높이 조절
                shape = RoundedCornerShape(8.dp),
                clip = false
            )
            .background(Color(0xFFE9F8FE), shape = RoundedCornerShape(16.dp)) // 파란색 배경과 둥근 모서리 적용
            .clickable { navController.navigate("beggingMoney") } // 클릭 시 화면 이동
    ) {
        // 가운데 텍스트
        Text(
            text = "용돈 조르기",
            fontSize = 40.sp,
            fontWeight = FontWeight.W900,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Center) // 가운데 정렬
        )

        // 오른쪽 하단 이미지
        Image(
            painter = painterResource(id = R.drawable.character_young_man),
            contentDescription = "Character",
            modifier = Modifier
                .size(150.dp) // 이미지 크기 조절
                .align(Alignment.BottomEnd) // 오른쪽 하단에 배치
        )
    }
}

@Composable
fun BeggingMission(navController: NavController, boxHeight: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(boxHeight)
            .shadow(
                elevation = 12.dp, // 그림자 높이 조절
                shape = RoundedCornerShape(8.dp),
                clip = false
            )
            .background(Color(0xFFFFEDEF), shape = RoundedCornerShape(16.dp)) // 파란색 배경과 둥근 모서리 적용
            .clickable { navController.navigate("beggingMission") } // 클릭 시 화면 이동
    ) {
        // 가운데 텍스트
        Text(
            text = "미션 관리",
            fontSize = 40.sp,
            fontWeight = FontWeight.W900,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Center) // 가운데 정렬
        )

        // 오른쪽 하단 이미지
        Image(
            painter = painterResource(id = R.drawable.character_young_girl),
            contentDescription = "Character",
            modifier = Modifier
                .size(150.dp) // 이미지 크기 조절
                .align(Alignment.BottomEnd) // 오른쪽 하단에 배치
        )
    }
}