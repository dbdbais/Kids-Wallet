package com.ssafy.kidswallet.ui.screens.card

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top
import com.ssafy.kidswallet.ui.components.LightGrayButton

@Composable
fun Card2Screen(navController: NavController) {
    val selectedCardIndex = remember { mutableStateOf(0) }

    // 카드 리스트
    val cards = listOf(
        R.drawable.card_green1_shadow to "그린1 카드",
        R.drawable.card_pink1_shadow to "핑크1 카드",
        R.drawable.card_black1_shadow to "블랙1 카드",
        R.drawable.card_white_shadow to "화이트 카드",
    )

    // 다음 카드로 이동
    fun nextCard() {
        selectedCardIndex.value = (selectedCardIndex.value + 1) % cards.size
    }

    // 이전 카드로 이동
    fun previousCard() {
        selectedCardIndex.value = if (selectedCardIndex.value - 1 < 0) cards.size - 1 else selectedCardIndex.value - 1
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Top(title = "카드", navController = navController)
        Spacer(modifier = Modifier.height(16.dp))

        // 진행바
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(Color(0xFF6DCEF5), Color(0xFF6DCEF5), Color(0xFFE0E0E0)).forEach { color ->
                Box(
                    modifier = Modifier
                        .width(110.dp)
                        .fillMaxHeight(0.02f)
                        .background(color, shape = RoundedCornerShape(4.dp))
                        .border(1.dp, color, shape = RoundedCornerShape(4.dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 가운데 정렬된 텍스트
        Text(
            text = "이런 카드는 어때요?",
            style = FontSizes.h32,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(100.dp))

        // 카드 섹션
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.Center
        ) {
            cards.forEachIndexed { index, (cardRes, description) ->
                val isSelected = selectedCardIndex.value == index
                val cardWidth by animateDpAsState(targetValue = if (isSelected) 180.dp else 160.dp,
                    label = ""
                )
                val cardHeight by animateDpAsState(targetValue = if (isSelected) 280.dp else 250.dp,
                    label = ""
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable(
                            onClick = { selectedCardIndex.value = index },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                ) {

                    Image(
                        painter = painterResource(id = cardRes),
                        contentDescription = description,
                        modifier = Modifier
                            .width(cardWidth)
                            .height(cardHeight)
                            .background(
                                color = if (isSelected) Color.LightGray.copy(alpha = 0.3f) else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(4.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    Image(
                        painter = painterResource(
                            id = if (isSelected) R.drawable.icon_card_check else R.drawable.icon_card_noncheck
                        ),
                        contentDescription = if (isSelected) "카드 체크 됨" else "카드 체크 안됨",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { selectedCardIndex.value = index }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))


        if (selectedCardIndex.value != -1) {
            BlueButton(
                onClick = { navController.navigate("card3") },
                text = "다음",
                modifier = Modifier
                    .width(400.dp)
                    .padding(bottom = 20.dp)
            )
        } else {
            LightGrayButton(
                onClick = { /* 아무것도 하지 않음 */ },
                text = "다음",
                modifier = Modifier
                    .width(400.dp)
                    .padding(bottom = 20.dp)
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
fun Card2ScreenPreview() {
    val navController = rememberNavController()
    Card2Screen(navController = navController)
}
