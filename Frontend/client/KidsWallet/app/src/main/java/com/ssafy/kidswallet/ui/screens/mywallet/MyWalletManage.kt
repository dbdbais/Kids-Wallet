package com.ssafy.kidswallet.ui.screens.mywallet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.viewmodel.AccountWeeklyViewModel
import com.ssafy.kidswallet.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun MyWalletManageScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    accountWeeklyViewModel: AccountWeeklyViewModel = viewModel()
) {
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
    val weeklyData = accountWeeklyViewModel.accountWeeklyData.collectAsState().value

    // API 호출을 통해 데이터 로드
    storedUserData?.representAccountId?.let { accountId ->
        LaunchedEffect(accountId) {
            accountWeeklyViewModel.fetchAccountWeekly(accountId)
        }
    }

    val curSpentMoney = weeklyData?.curSpentMoney ?: 0
    val curIncomeMoney = weeklyData?.curIncomeMoney ?: 0
    val balanceDifference = curIncomeMoney - curSpentMoney
    val dailyAmounts = weeklyData?.curListSpent ?: listOf(0, 0, 0, 0, 0, 0, 0)
    val maxAmount = dailyAmounts.maxOrNull() ?: 0

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
                text = "${NumberUtils.formatNumberWithCommas(balanceDifference)}원 많아요",
                color = Color.Black,
                style = FontSizes.h24,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "+${NumberUtils.formatNumberWithCommas(curIncomeMoney)}원",
                    color = Color(0xFFFF0606),
                    style = FontSizes.h16,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "-${NumberUtils.formatNumberWithCommas(curSpentMoney)}원",
                    color = Color(0xFF3290FF),
                    style = FontSizes.h16,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        // Weekly Expense Summary Card with white background
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            shape = RoundedCornerShape(45.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
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
                            append("${NumberUtils.formatNumberWithCommas(curSpentMoney)}원")
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
                            val prevSpentMoney = weeklyData?.prevSpentMoney ?: 0
                            val spentDifference = curSpentMoney - prevSpentMoney
                            append("${NumberUtils.formatNumberWithCommas(spentDifference)}원")
                        }
                        append(" 더 썼어요")
                    },
                    style = FontSizes.h16,
                    fontWeight = FontWeight.Bold,
                    color = Color(0x808C8595)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Bar Chart with each day aligned at the bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        val days = listOf("월", "화", "수", "목", "금", "토", "일")
                        dailyAmounts.forEachIndexed { index, amount ->
                            DayExpenseBar(day = days[index], amount = amount, maxAmount = maxAmount)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DayExpenseBar(day: String, amount: Int, maxAmount: Int) {
    // 바의 최대 높이를 250dp로 설정
    val maxBarHeight = 250.dp
    // 현재 금액에 따라 바 길이를 비례적으로 계산
    val barHeight = if (maxAmount > 0) {
        (amount.toFloat() / maxAmount * maxBarHeight.value).dp
    } else {
        0.dp
    }

    Box(
        modifier = Modifier
            .width(40.dp) // Bar와 금액 텍스트의 width가 겹치지 않도록 설정
            .height(maxBarHeight + 40.dp), // 최대 높이에 텍스트 공간 추가
        contentAlignment = Alignment.BottomCenter
    ) {
        // 금액 텍스트를 Bar와 겹치지 않게 Box의 상단에 별도로 배치
        if (amount > 0) {
            Text(
                text = NumberUtils.formatNumberWithCommas(amount),
                style = FontSizes.h16,
                color = Color(0xFF3290FF),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-30).dp) // 위로 위치를 조정하여 Bar와 겹치지 않도록
            )
        }

        // Bar와 요일 텍스트를 독립적인 Column으로 배치
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxHeight()
        ) {
            // Bar itself
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(barHeight)
                    .background(Color(0xFF3290FF), shape = RoundedCornerShape(4.dp)) // 둥근 모서리 적용
            )

            // Day text (below the bar)
            Text(
                text = day,
                style = FontSizes.h16,
                color = Color(0x808C8595),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
