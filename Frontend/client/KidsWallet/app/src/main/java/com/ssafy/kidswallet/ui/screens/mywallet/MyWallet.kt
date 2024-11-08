import androidx.compose.runtime.*
import java.util.Calendar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.alpha
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top

@Composable
fun MyWalletScreen(navController: NavController) {
    // Initialize Calendar instance for current date
    val calendar = Calendar.getInstance()
    var currentMonth by remember { mutableStateOf(calendar.get(Calendar.MONTH) + 1) } // 0-based month, +1 for display
    var currentYear by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    val role = " "

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
                .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 16.dp)
        ) {
            Column {
                Text(
                    text = "남은 돈",
                    style = FontSizes.h16,
                    color = Color(0xFF5EA0BB),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "500원",
                    style = FontSizes.h32,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ClickableIconWithText(
                        navController = navController,
                        imageId = R.drawable.icon_wallet,
                        text = "지출 관리",
                        route = "mainPage", // 첫 번째 아이콘 클릭 시 이동할 경로
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    ClickableIconWithText(
                        navController = navController,
                        imageId = R.drawable.icon_withrow,
                        text = "보내기",
                        route = "mainPage", // 두 번째 아이콘 클릭 시 이동할 경로
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    ClickableIconWithText(
                        navController = navController,
                        imageId = R.drawable.icon_deposit,
                        text = "채우기",
                        route = "mainPage", // 세 번째 아이콘 클릭 시 이동할 경로
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton( onClick = {
                    // role에 따라 라우팅 처리
                    if (role == "parents") {
                        navController.navigate("myWallet") // "parents"일 경우 특정 경로로 이동
                    } else {
                        navController.navigate("begging") // "children"일 경우 다른 경로로 이동
                    }
                }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(), // Row가 가득 채워지도록 설정
                        horizontalArrangement = Arrangement.SpaceBetween, // 요소들을 양 끝으로 배치
                        verticalAlignment = Alignment.CenterVertically // 세로로 중앙 정렬
                    ) {
                        if (role == "parents") {
                            // role이 "parents"일 경우 보이도록 설정
                            Text(
                                text = "내 아이 지갑 같이보기",
                                style = FontSizes.h16,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Start
                            )
                            Image(
                                painter = painterResource(id = R.drawable.icon_next_white), // 커스텀 아이콘
                                contentDescription = "클릭 버튼",
                                modifier = Modifier.size(16.dp) // 크기 조정 가능
                            )
                        } else {
                            Text(
                                text = "조르러 가기",
                                style = FontSizes.h16,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Start
                            )
                            Image(
                                painter = painterResource(id = R.drawable.icon_next_white), // 커스텀 아이콘
                                contentDescription = "클릭 버튼",
                                modifier = Modifier.size(16.dp) // 크기 조정 가능
                            )
                        }
                    }
                }


            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        // Calendar Section with Rounded Gray Background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFF7F7F7), // 원하는 배경색
                    shape = RoundedCornerShape(16.dp) // 라운드 모서리
                )
                .padding(12.dp) // 내부 여백 설정
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Define range: 2023년 11월 ~ 2024년 11월
                val startMonth = 11
                val startYear = 2023
                val endMonth = 11
                val endYear = 2024

                val isFirstMonth = currentMonth == startMonth && currentYear == startYear
                val isLastMonth = currentMonth == endMonth && currentYear == endYear

                IconButton(
                    onClick = {
                        // 이전 달로 이동
                        if (!isFirstMonth) {
                            if (currentMonth == 1) {
                                currentMonth = 12
                                currentYear -= 1
                            } else {
                                currentMonth -= 1
                            }
                        }
                    },
                    enabled = !isFirstMonth, // 버튼 비활성화
                    modifier = Modifier.alpha(if (isFirstMonth) 0f else 1f) // 버튼을 안보이게 하되 공간은 차지
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_back), // 커스텀 아이콘
                        contentDescription = "이전 달",
                        modifier = Modifier.size(24.dp) // 크기 조정 가능
                    )
                }
                Text(
                    text = "${currentYear}년 ${currentMonth}월",
                    style = FontSizes.h20,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp) // 텍스트 양쪽 간격
                )
                IconButton(
                    onClick = {
                        // 다음 달로 이동
                        if (!isLastMonth) {
                            if (currentMonth == 12) {
                                currentMonth = 1
                                currentYear += 1
                            } else {
                                currentMonth += 1
                            }
                        }
                    },
                    enabled = !isLastMonth, // 버튼 비활성화
                    modifier = Modifier.alpha(if (isLastMonth) 0f else 1f) // 버튼을 안보이게 하되 공간은 차지
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_next), // 커스텀 아이콘
                        contentDescription = "다음 달",
                        modifier = Modifier.size(24.dp) // 크기 조정 가능
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        // No Transaction Section
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
    }
}

@Composable
fun ClickableIconWithText(
    navController: NavController,
    imageId: Int,
    text: String,
    route: String,
    modifier: Modifier = Modifier // 추가된 modifier 매개변수
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { navController.navigate(route) }
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = text,
            modifier = Modifier.size(60.dp)
        )
        Text(
            text = text,
            style = FontSizes.h16,
            color = Color.Black
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
