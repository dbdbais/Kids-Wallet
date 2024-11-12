package com.ssafy.kidswallet.ui.screens.mywallet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.ui.components.Top

@Composable
fun MyWalletManageScreen(navController: NavController,) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Top(title = "지출 관리", navController = navController)

        // Balance summary
        Text(
            text = "나간 돈 보다 들어온 돈이",
            color = Color.Gray,
            fontSize = 16.sp
        )
        Text(
            text = "3,500원 많아요",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "+5,000원", color = Color.Red, fontSize = 16.sp)
            Text(text = "-1,500원", color = Color.Blue, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Weekly Expense Summary Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "이번주 동안",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "1,500원 썼어요",
                    fontSize = 24.sp,
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "지난주 보다 1,500원 더 썼어요",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Bar Chart (Static Example)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
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

@Composable
fun DayExpenseBar(day: String, amount: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (amount > 0) {
            Text(
                text = "${amount}원",
                fontSize = 12.sp,
                color = Color.Blue,
                textAlign = TextAlign.Center
            )
        }
        Box(
            modifier = Modifier
                .width(20.dp)
                .height((amount / 10).dp) // Simple scaling for demo
                .background(Color.Blue)
        )
        Text(
            text = day,
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
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
