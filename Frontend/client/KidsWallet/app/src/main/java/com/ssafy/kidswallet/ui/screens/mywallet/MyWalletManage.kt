package com.ssafy.kidswallet.ui.screens.mywallet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top

@Composable
fun MyWalletManageScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Top(title = "지출 관리", navController = navController)

        // Balance summary
        Column(
            modifier = Modifier.padding(start = 16.dp, top = 50.dp)
        ) {
            Text(
                text = "나간 돈 보다 들어온 돈이",
                color = Color(0x808C8595),
                style = FontSizes.h24,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "3,500원 많아요",
                color = Color.Black,
                style = FontSizes.h24,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "+5,000원",
                    color = Color(0xFFFF0606),
                    style = FontSizes.h16,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "-1,500원",
                    color = Color(0xFF3290FF),
                    style = FontSizes.h16,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(100.dp))

        // Weekly Expense Summary Card with white background
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            shape = RoundedCornerShape(45.dp), // 모서리를 둥글게 설정
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // 그림자 강도
            colors = CardDefaults.cardColors(containerColor = Color.White) // 카드 배경을 흰색으로 설정
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "이번주 동안",
                    style = FontSizes.h20,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color(0xFF3290FF))) {
                            append("1,500원")
                        }
                        append(" 썼어요")
                    },
                    style = FontSizes.h20,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = buildAnnotatedString {
                        append("지난주 보다 ")
                        withStyle(style = SpanStyle(color = Color(0x803290FF))) {
                            append("1,500원")
                        }
                        append(" 더 썼어요")
                    },
                    style = FontSizes.h16,
                    fontWeight = FontWeight.Bold,
                    color = Color(0x808C8595)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Bar Chart with each day aligned at the bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White) // 차트 배경 흰색
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom // 요일을 하단에 정렬
                    ) {
                        DayExpenseBar(day = "월", amount = 0)
                        DayExpenseBar(day = "화", amount = 0)
                        DayExpenseBar(day = "수", amount = 500)
                        DayExpenseBar(day = "목", amount = 0)
                        DayExpenseBar(day = "금", amount = 0)
                        DayExpenseBar(day = "토", amount = 1500)
                        DayExpenseBar(day = "일", amount = 0)
                    }
                }
            }
        }
    }
}

@Composable
fun DayExpenseBar(day: String, amount: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        // Bar amount text (above the bar)
        if (amount > 0) {
            Text(
                text = "${amount}원",
                fontSize = 12.sp,
                color = Color(0xFF3290FF),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        // Bar itself
        Box(
            modifier = Modifier
                .width(20.dp)
                .height((amount / 10).dp) // 금액에 따른 동적 높이 설정
                .background(Color(0xFF3290FF), shape = MaterialTheme.shapes.small)
        )
        // Day text (below the bar)
        Text(
            text = day,
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560",
    showSystemUi = true
)
@Composable
fun MyWalletManageScreenPreview() {
    MyWalletManageScreen(navController = rememberNavController())
}
