package com.ssafy.kidswallet.ui.screens.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.kidswallet.ui.components.Top

@Composable
fun CardScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Top(title = "카드", navController = navController) // BackButton 사용
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "카드 신청 화면", style = MaterialTheme.typography.headlineMedium)
    }
}