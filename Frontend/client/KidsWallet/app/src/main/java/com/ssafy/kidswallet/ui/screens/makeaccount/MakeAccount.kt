package com.ssafy.kidswallet.ui.screens.makeaccount

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.Top

@Composable
fun MakeAccountScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Top(title = "통장 개설", navController = navController)

        Spacer(modifier = Modifier.height(24.dp))

        // 여러 개의 약관 항목
        val terms = listOf(
            "이용 약관 1",
            "이용 약관 2",
            "이용 약관 3",
            "이용 약관 4"
        )
        val expandedStates = remember { mutableStateListOf(*Array(terms.size) { false }) }

        terms.forEachIndexed { index, termTitle ->
            // 약관 제목
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                   Text(
                       text = "(필수)",
                       color = Color.Red,
                       fontWeight = FontWeight.Bold
                   )
                    Text(
                        text = if (expandedStates[index]) "$termTitle ▷" else "$termTitle ▽",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandedStates[index] = !expandedStates[index] }
                            .padding(8.dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = if (expandedStates[index]) "▷" else "▽",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandedStates[index] = !expandedStates[index] }
                        .padding(8.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

            }

            // 약관 내용
            AnimatedVisibility(
                visible = expandedStates[index],
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = "$termTitle 내용: 이 서비스는 ...",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        text = "사용자는 ...",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // 확인 버튼
        BlueButton(
            onClick = {
                // 확인 버튼 클릭 시 동작 추가 가능
            },
            height = 40,
            text = "동의 후 개설하기"
        )
    }
}