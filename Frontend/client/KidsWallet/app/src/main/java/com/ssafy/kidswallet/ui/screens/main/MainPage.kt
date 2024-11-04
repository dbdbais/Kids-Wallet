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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.kidswallet.ui.components.Top

@Composable
fun MainPageScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Bar
        Top(title = "메인 페이지", navController = navController)

        Spacer(modifier = Modifier.height(16.dp))

        // 메뉴 목록
        Text(
            text = "카드 신청",
            modifier = Modifier.clickable { navController.navigate("card") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "용돈 조르기",
            modifier = Modifier.clickable { navController.navigate("begging") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "퀴즈",
            modifier = Modifier.clickable { navController.navigate("quiz") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "행복 달리기",
            modifier = Modifier.clickable { navController.navigate("runParents") }
        )
    }
}

@Composable
fun CardScreen(navController: NavController) {
    // CardScreen 구현 내용
}

@Composable
fun BeggingScreen(navController: NavController) {
    // BeggingScreen 구현 내용
}

@Composable
fun QuizScreen(navController: NavController) {
    // QuizScreen 구현 내용
}

@Composable
fun RunParentsScreen(navController: NavController) {
    // RunParentsScreen 구현 내용
}

