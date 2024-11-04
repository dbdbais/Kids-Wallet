package com.ssafy.kidswallet.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.kidswallet.R

@Composable
fun BackButton(navController: NavController) {
    Image(
        painter = painterResource(id = R.drawable.icon_back),
        contentDescription = "Back",
        modifier = Modifier
            .size(24.dp)
            .clickable {
                navController.popBackStack() // 이전 화면으로 돌아가기
            }
            .padding(8.dp)
    )
}

// BackButton말고 Top쓰세요!!