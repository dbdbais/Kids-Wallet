package com.ssafy.kidswallet.ui.screens.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.ui.screens.begging.beggingmoney.BeggingMoneyScreen
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun CardScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Top(title = "카드", navController = navController) // BackButton 사용
        Spacer(modifier = Modifier.height(16.dp))
        // 진행바
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Box(
                modifier = Modifier
                    .width(110.dp)
                    .fillMaxHeight(0.02f)
                    .background(Color(0xFF6DCEF5), shape = RoundedCornerShape(4.dp))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE0E0E0),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
            Box(
                modifier = Modifier
                    .width(110.dp)
                    .fillMaxHeight(0.02f)
                    .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(4.dp))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE0E0E0),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
            Box(
                modifier = Modifier
                    .width(110.dp)
                    .fillMaxHeight(0.02f)
                    .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(4.dp))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE0E0E0),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        // 텍스트
        Column (
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp),
                text = "나만의 카드를 만들어보자",
                style = FontSizes.h32,
                fontWeight = FontWeight.Black,
                color = Color(0xFFC5C1C9)
            )
            GradientWalletText()
        }

        Spacer(modifier = Modifier.height(40.dp))

        // 카드
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // 왼쪽의 그린 카드
            Image(
                painter = painterResource(id = R.drawable.card3),
                contentDescription = "그린 카드",
                modifier = Modifier
                    .size(200.dp) // 조금 더 작은 크기
                    .offset(x = (-120).dp, y = 15.dp) // 왼쪽으로 이동
                    .rotate(-30f) // 왼쪽으로 기울이기
                    .zIndex(1f) // 블랙카드보다 아래
            )

            // 중앙의 블랙 카드
            Image(
                painter = painterResource(id = R.drawable.card5),
                contentDescription = "블랙 카드",
                modifier = Modifier
                    .size(270.dp) // 가장 크게 설정
                    .zIndex(2f) // 가장 위에 위치
            )

            // 오른쪽의 핑크 카드
            Image(
                painter = painterResource(id = R.drawable.card7),
                contentDescription = "핑크 카드",
                modifier = Modifier
                    .size(200.dp) // 조금 더 작은 크기
                    .offset(x = 120.dp, y = 15.dp) // 오른쪽으로 이동
                    .rotate(30f) // 오른쪽으로 기울이기
                    .zIndex(1f) // 블랙카드보다 아래
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 텍스트
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "연회비",
                color = Color(0xFF8C8595),
                style = FontSizes.h16,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp)) // 간격 조절
            Text(
                text = "없음",
                color = Color(0xFF8C8595).copy(alpha = 0.5f),
                style = FontSizes.h16,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp)) // "|"와의 간격
            Text(
                text = "|",
                color = Color(0xFF8C8595),
                style = FontSizes.h16,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp)) // "브랜드"와의 간격
            Text(
                text = "브랜드",
                color = Color(0xFF8C8595),
                style = FontSizes.h16,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp)) // 간격 조절
            Text(
                text = "국내 전용",
                color = Color(0xFF8C8595).copy(alpha = 0.5f),
                style = FontSizes.h16,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 유의사항 및 약관 텍스트
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp) // 스크롤 박스 높이 지정
                .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(8.dp)) // 배경 및 모서리 둥글게
                .border(width = 1.dp, color = Color(0xFFFFFFFF), shape = RoundedCornerShape(8.dp))
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "유의사항",
                    style = FontSizes.h12,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8C8595)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = """
        1. 연회비: KidsWallet 체크카드는 연회비가 없습니다.
        2. 사용처: 해당 체크카드는 국내 전용으로, 해외 가맹점에서는 사용할 수 없습니다.
        3. 결제 한도: 보호자가 설정한 한도 내에서만 사용 가능합니다.
        4. 분실 및 도난 시: 카드를 분실하거나 도난당한 경우, 즉시 KidsWallet 고객센터 또는 앱에서 사용 중지를 요청하세요.
        5. 거래 내역 조회: 보호자는 실시간으로 자녀의 거래 내역을 조회할 수 있습니다.
        6. 환불 및 취소: 카드로 결제한 내역에 대한 환불은 가맹점 정책에 따릅니다.
        7. 수수료: ATM 인출 및 이체 시 수수료가 발생할 수 있으며, 금융기관별로 다를 수 있습니다.
        8. 사용 제한: 미성년자에게 적합하지 않은 업종에서는 사용이 제한될 수 있습니다.
    """.trimIndent(),
                    fontSize = 10.sp,
                    color = Color(0xFF8C8595).copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "이용약관",
                    style = FontSizes.h12,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8C8595)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = """
        1. 서비스 제공: KidsWallet 체크카드 서비스는 KidsWallet 회원에게 제공됩니다.
        2. 개인정보 보호: KidsWallet은 회원의 개인정보를 안전하게 보호하기 위해 필요한 조치를 이행합니다.
        3. 카드 발급 및 해지: 체크카드 발급 시 보호자의 동의가 필요하며, 해지는 언제든지 가능합니다.
        4. 부정 사용에 대한 책임: 분실 신고 이전의 부정 사용에 대해서는 책임을 지지 않습니다.
        5. 약관 변경: KidsWallet은 서비스 개선을 위해 본 약관을 변경할 수 있습니다.
        6. 준거법 및 관할: 본 약관은 대한민국 법률을 준거로 하며, 분쟁 발생 시 대한민국 법원에서 관할합니다.
    """.trimIndent(),
                    fontSize = 10.sp,
                    color = Color(0xFF8C8595).copy(alpha = 0.5f)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // 버튼
        BlueButton(
            onClick = { navController.navigate("card2") },
            text = "다음",
            modifier = Modifier
                .width(400.dp)
                .padding(bottom = 20.dp)
        )

        // 하단바
    }
}

@Composable
fun GradientWalletText() {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFF6DCEF5), Color(0xFF99DDF8))
    )

    BasicText(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(brush = gradientBrush)) {
                append("KID'S WALLET")
            }
        },
        style = FontSizes.h40.copy(fontWeight = FontWeight.Black, letterSpacing = 4.sp),
    )
}

@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560", // Galaxy S24 Ultra 해상도에 맞추기
    showSystemUi = true
)
@Composable
fun CardScreenPreview() {
    // 임시 NavController를 생성하여 프리뷰에서 사용
    val navController = rememberNavController()
    CardScreen(navController = navController)
}