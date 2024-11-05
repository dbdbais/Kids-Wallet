package com.ssafy.kidswallet.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssafy.kidswallet.R

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier, navController: NavController) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .drawBehind {
                val shadowHeight = 4.dp.toPx()

                // 상단 그림자
                withTransform({
                    translate(top = -shadowHeight)
                }) {
                    drawRect(
                        color = Color.Gray.copy(alpha = 0.3f),
                        size = size.copy(height = shadowHeight)
                    )
                }
            },
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_alert),
            contentDescription = "Alert",
            modifier = Modifier.size(40.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.home),
            contentDescription = "Home",
            modifier = Modifier
                .size(64.dp)
                .clickable {
                    navController.navigate("mainPage") {
                        popUpTo("mainPage") { inclusive = true }
                    }
                }
        )
        Image(
            painter = painterResource(id = R.drawable.icon_regular),
            contentDescription = "Regular",
            modifier = Modifier.size(40.dp)
        )
    }
}

