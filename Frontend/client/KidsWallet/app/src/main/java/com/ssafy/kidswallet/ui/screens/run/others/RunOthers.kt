package com.ssafy.kidswallet.ui.screens.run.others

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) 
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
            contentDescription = "로고 아이콘",
            modifier = Modifier.size(200.dp)
        )
    }
}

@Composable
fun RunOtherContentSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_runothers),
            contentDescription = "후원",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f) 
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray.copy(alpha = 0.7f)) 
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center) 
                .padding(bottom = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_prepare),
                contentDescription = "준비 중"
            )
            Text(
                text = "COMMING SOON",
                style = FontSizes.h32,
                color = Color(0xFF686CDF),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Divider(
                color = Color.Black,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "현재 서비스 준비중입니다.\n이용에 불편을 드려 대단히 죄송합니다.\n빠른 시일 내에 준비하여 찾아뵙겠습니다.",
                style = FontSizes.h16,
                color = Color.Black,

                textAlign = TextAlign.Center 
            )

            Spacer(modifier = Modifier.height(8.dp))

            Divider(
                color = Color.Black,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }
    }
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
