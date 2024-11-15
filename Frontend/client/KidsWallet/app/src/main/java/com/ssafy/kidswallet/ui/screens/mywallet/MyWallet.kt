package com.ssafy.kidswallet.ui.screens.mywallet

import androidx.compose.runtime.*
import java.util.Calendar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import androidx.compose.material3.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.alpha
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.kidswallet.data.model.TransactionModel
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.viewmodel.LoginViewModel
import com.ssafy.kidswallet.viewmodel.AccountTransactionViewModel

@Composable
fun MyWalletScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    accountTransactionViewModel: AccountTransactionViewModel = viewModel()
) {
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
    val accountState by accountTransactionViewModel.accountState.collectAsState()
    val errorState by accountTransactionViewModel.errorState.collectAsState()

    // API 요청 - representAccountId가 null이 아닐 때만 호출
    LaunchedEffect(storedUserData?.representAccountId) {
        storedUserData?.representAccountId?.let { accountId ->
            accountTransactionViewModel.getTransactionData(accountId)
        }
    }

    // 캘린더 초기화
    val calendar = Calendar.getInstance()
    var currentMonth by remember { mutableStateOf(calendar.get(Calendar.MONTH) + 1) } // 0-based month, +1 for display
    var currentYear by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    val startMonth = 11
    val startYear = 2023
    val endMonth = 11
    val endYear = 2024

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Top(title = "내 지갑", navController = navController)

        Spacer(modifier = Modifier.height(50.dp))

        // Main Card Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(25.dp)
                )
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF99DDF8), Color(0xFF6DCEF5))
                    ),
                    shape = RoundedCornerShape(25.dp)
                )
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 25.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "남은 돈",
                            style = FontSizes.h16,
                            color = Color(0xFF5EA0BB),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = accountState?.data?.firstOrNull()?.curBalance?.let {
                                "${NumberUtils.formatNumberWithCommas(it)}원" } ?: "0원",
                            style = FontSizes.h32,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ClickableIconWithText(
                        navController = navController,
                        imageId = R.drawable.icon_wallet,
                        text = "지출 관리",
                        route = "myWalletManage",
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    ClickableIconWithText(
                        navController = navController,
                        imageId = R.drawable.icon_withrow,
                        text = "보내기",
                        route = "myWalletTransfer",
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    ClickableIconWithText(
                        navController = navController,
                        imageId = R.drawable.icon_deposit,
                        text = "채우기",
                        route = "myWalletDeposit",
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    ClickableIconWithText(
                        navController = navController,
                        imageId = R.drawable.icon_bundle,
                        text = "빼기",
                        route = "myWalletWithdraw",
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = {
                        if (storedUserData?.userRole == "CHILD") {
                            navController.navigate("begging")
                        } else {
                            navController.navigate("myWallet")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF6DCEF5),
                            shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (storedUserData?.userRole == "CHILD") {
                            Text(
                                text = "조르러 가기",
                                style = FontSizes.h20,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    top = 8.dp,
                                    bottom = 8.dp
                                )
                            )
                            Image(
                                painter = painterResource(id = R.drawable.icon_next_white),
                                contentDescription = "클릭 버튼",
                                modifier = Modifier.size(16.dp)
                            )
                        } else {
                            Text(
                                text = "내 아이 지갑 같이보기",
                                style = FontSizes.h20,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    top = 8.dp,
                                    bottom = 8.dp
                                )
                            )
                            Image(
                                painter = painterResource(id = R.drawable.icon_next_white),
                                contentDescription = "클릭 버튼",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Calendar Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFF7F7F7),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(start = 8.dp, end = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isFirstMonth = currentMonth == startMonth && currentYear == startYear
                val isLastMonth = currentMonth == endMonth && currentYear == endYear

                IconButton(
                    onClick = {
                        if (!isFirstMonth) {
                            if (currentMonth == 1) {
                                currentMonth = 12
                                currentYear -= 1
                            } else {
                                currentMonth -= 1
                            }
                        }
                    },
                    enabled = !isFirstMonth,
                    modifier = Modifier.alpha(if (isFirstMonth) 0f else 1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_back),
                        contentDescription = "이전 달",
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = "${currentYear}년 ${currentMonth}월",
                    style = FontSizes.h20,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                IconButton(
                    onClick = {
                        if (!isLastMonth) {
                            if (currentMonth == 12) {
                                currentMonth = 1
                                currentYear += 1
                            } else {
                                currentMonth += 1
                            }
                        }
                    },
                    enabled = !isLastMonth,
                    modifier = Modifier.alpha(if (isLastMonth) 0f else 1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_next),
                        contentDescription = "다음 달",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 거래 내역 표시
        val filteredTransactions = accountState?.data?.filter { transaction ->
            transaction.transactionDate[0] == currentYear && transaction.transactionDate[1] == currentMonth
        }

        if (filteredTransactions.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(50.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_no_transaction),
                    contentDescription = "거래내역 없음",
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "거래내역이 없어요",
                    style = FontSizes.h20,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                filteredTransactions.forEach { transaction ->
                    TransactionItem(
                        transaction = transaction)
                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: TransactionModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = transaction.message,
                style = FontSizes.h16,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${transaction.transactionDate[0]}.${transaction.transactionDate[1]}.${transaction.transactionDate[2]}",
                style = FontSizes.h12,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8C8595)
            )
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            val formattedAmount = NumberUtils.formatNumberWithCommas(transaction.amount)
            Text(
                text = if (transaction.transactionType == "WITHDRAWAL") "-${formattedAmount}원" else "+${formattedAmount}원",
                style = FontSizes.h16,
                color = if (transaction.transactionType == "WITHDRAWAL") Color.Blue else Color.Red,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "잔액 ${NumberUtils.formatNumberWithCommas(transaction.curBalance)}원",
                style = FontSizes.h12,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8C8595)
            )
        }
    }
}



@Composable
fun ClickableIconWithText(
    navController: NavController,
    imageId: Int,
    text: String,
    route: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable { navController.navigate(route) }
            .padding(start = 16.dp, bottom = 16.dp)
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = text,
            modifier = Modifier.size(60.dp)
        )
        Text(
            text = text,
            style = FontSizes.h16,
            color = Color.White
        )
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560",
    showSystemUi = true
)
@Composable
fun MyWalletScreenPreview() {
    MyWalletScreen(navController = rememberNavController())
}
