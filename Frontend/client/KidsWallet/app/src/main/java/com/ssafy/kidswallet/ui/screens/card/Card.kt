package com.ssafy.kidswallet.ui.screens.card

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.ui.screens.begging.beggingmoney.BeggingMoneyScreen

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
        // 카드
        Row(

        ) {

        }
        // 텍스트
        Row(

        ) {

        }
        // 버튼

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