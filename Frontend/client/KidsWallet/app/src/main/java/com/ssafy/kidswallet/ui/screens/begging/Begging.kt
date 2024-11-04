package com.ssafy.kidswallet.ui.screens.begging

import androidx.compose.foundation.layout.Arrangement
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
import androidx.navigation.NavHostController
import com.ssafy.kidswallet.ui.components.BackButton
import com.ssafy.kidswallet.ui.components.Top

@Composable
fun BeggingScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Top(title = "조르기", navController = navController) // BackButton 사용
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "용돈 조르기 화면", style = MaterialTheme.typography.headlineMedium)
    }
}