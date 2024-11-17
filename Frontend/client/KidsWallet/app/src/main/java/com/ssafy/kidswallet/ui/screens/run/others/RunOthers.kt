package com.ssafy.kidswallet.ui.screens.run.others

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top

@Composable
fun RunOthersScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RunOtherTopSection(navController = navController)
        Spacer(modifier = Modifier.height(16.dp))
        RunOtherHeaderSection()
        RunOtherContentSection()
        // 이미지 섹션을 감싸는 Column에 weight 적용
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // 남은 여백을 채우도록 설정
        ) {
            RunOtherImage()
        }
    }
}

@Composable
fun RunOtherTopSection(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Top(title = "함께 달리기", navController = navController)
    }
}

@Composable
fun RunOtherHeaderSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE3F5FC), RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_full),
            contentDescription = "송금 아이콘",
            modifier = Modifier.size(200.dp)
        )
    }
}

@Composable
fun RunOtherContentSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "키즈월렛과 함께 달리며\n행복을 나누세요!!",
            style = FontSizes.h20,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "이런 캠페인은 어떤가요?",
            style = FontSizes.h20,
            textAlign = TextAlign.Start,
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
    }
}

@Composable
fun RunOtherImage() {
    Image(
        painter = painterResource(id = R.drawable.logo_runothers),
        contentDescription = "후원",
        modifier = Modifier
            .fillMaxSize() // 전체 크기를 채우도록 설정
            .padding(horizontal = 16.dp)
    )
}

@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560",
    showSystemUi = true
)
@Composable
fun RunOthersScreenPreview() {
    RunOthersScreen(navController = rememberNavController())
}
