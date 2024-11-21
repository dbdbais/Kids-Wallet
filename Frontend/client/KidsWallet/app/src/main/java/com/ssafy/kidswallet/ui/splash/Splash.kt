package com.ssafy.kidswallet.ui.splash

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.screens.run.parents.RunParentsScreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    var startTextAnimation by remember { mutableStateOf(false) }
    var startImageAnimation by remember { mutableStateOf(false) }

    val textAlpha by animateFloatAsState(
        targetValue = if (startTextAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1100)
    )

    val imageAlpha by animateFloatAsState(
        targetValue = if (startImageAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1100)
    )

    LaunchedEffect(Unit) {
        startTextAnimation = true
        delay(700)
        startImageAnimation = true
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Column (
                modifier = Modifier.alpha(textAlpha),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "같이",
                        style = FontSizes.h40,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6DCEF5)
                    )
                    Text(
                        text = "의",
                        style = FontSizes.h32,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "가치",
                        style = FontSizes.h40,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6DCEF5)
                    )
                    Text(
                        text = "를",
                        style = FontSizes.h32,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "실현하다!",
                    style = FontSizes.h32,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = R.drawable.logo_full),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(300.dp)
                    .alpha(imageAlpha)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, bottom = 32.dp, end = 32.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            Divider(
                color = Color(0xFFD9D9D9),
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Supported by",
                    style = FontSizes.h16,
                    modifier = Modifier.padding(top = 6.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "아이와 함께 춤을",
                    style = FontSizes.h20,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6DCEF5)
                )
            }
        }

        LaunchedEffect(Unit) {
            delay(2000)
            navController.navigate("loginRouting") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560",
    showSystemUi = true
)
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = rememberNavController())
}