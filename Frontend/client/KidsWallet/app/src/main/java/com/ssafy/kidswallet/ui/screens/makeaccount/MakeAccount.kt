package com.ssafy.kidswallet.ui.screens.makeaccount

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.Top
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.kidswallet.viewmodel.LoginViewModel
import com.ssafy.kidswallet.viewmodel.MakeAccountViewModel
import com.ssafy.kidswallet.viewmodel.UpdateUserViewModel


@Composable
fun MakeAccountScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel(), makeAccountViewModel: MakeAccountViewModel = viewModel(), updateUserViewModel: UpdateUserViewModel = viewModel()) {
    val storedUserData = loginViewModel.getStoredUserData().collectAsState().value
    val updatedUserData by updateUserViewModel.updatedUserData.collectAsState()

    val userId = storedUserData?.userId

    
    val checkedStates = remember { mutableStateListOf(*Array(4) { false }) }
    val expandedStates = remember { mutableStateListOf(*Array(4) { false }) }
    var allChecked by remember { mutableStateOf(false) }

    var isAccountRegistered by remember { mutableStateOf(false) }

    LaunchedEffect(isAccountRegistered) {
        if (isAccountRegistered && userId != null) {
            updateUserViewModel.updateUser(userId)
        }
    }

    LaunchedEffect(updatedUserData) {
        if (updatedUserData != null) {
            
            loginViewModel.saveUserData(updatedUserData!!)
            navController.navigate("mainPage") {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        
        Top(title = "통장 개설", navController = navController)

        Spacer(modifier = Modifier.height(24.dp))

        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE9F8FE)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        allChecked = !allChecked
                        checkedStates.fill(allChecked)
                    }
            ) {
                Checkbox(
                    checked = allChecked,
                    onCheckedChange = {
                        allChecked = it
                        checkedStates.fill(it)
                    },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF6DCEF5))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "모두 동의합니다",
                    fontWeight = FontWeight.Bold,
                    style = FontSizes.h16
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) 
                .padding(bottom = 32.dp) 
        ) {
            itemsIndexed(listOf(
                "서비스 이용 약관" to """
        본 약관은 KidsWallet 서비스의 이용과 관련하여 필요한 사항을 규정하며, 사용자와 KidsWallet 간의 권리와 의무를 명확히 합니다.
        사용자는 KidsWallet에서 제공하는 다양한 금융 관리 기능을 본인의 책임 하에 사용하며, 본 서비스가 제공하는 정보와 데이터에 대해 신뢰하는 것은 본인의 재량에 따릅니다.
        본 약관에 따라 KidsWallet 서비스는 예고 없이 개선, 수정, 중단할 수 있으며, 이러한 변경 사항은 앱 내 공지를 통해 사전 통지됩니다.
                """.trimIndent(),
                "개인정보 수집 및 이용 동의" to """
        본 약관은 사용자의 개인정보를 수집, 이용, 관리 및 보호하기 위한 내용으로 구성되어 있습니다. KidsWallet은 사용자의 이름, 연락처, 생년월일 등의 개인정보를 수집하며,
        이는 본인 인증, 계좌 개설 및 금융 서비스 제공을 위해 사용됩니다. 수집된 개인정보는 엄격한 보안 절차에 따라 보호되며, 법령에 따라 보존 기간이 만료되면 즉시 파기됩니다.
        개인정보 제공에 동의하지 않을 경우 일부 서비스 이용이 제한될 수 있습니다.
                 """.trimIndent(),
                "전자 금융 거래 약관" to """
        본 약관은 KidsWallet의 전자 금융 거래 서비스 이용 시 적용되는 규정을 명시합니다. 사용자는 KidsWallet을 통해 계좌 조회, 이체, 결제 등 다양한 금융 거래를 수행할 수 있으며,
        본 서비스는 안전한 거래 환경을 제공하기 위해 다양한 보안 기술을 적용하고 있습니다. 모든 거래 내역은 사용자의 거래 기록 보존을 위해 암호화되어 저장되며, 사용자는 거래 시 발생할 수 있는
        보안 위험을 인지하고 거래를 진행해야 합니다.
                """.trimIndent(),
                "개인신용정보 처리 동의" to """
        본 약관은 사용자 개인의 신용정보 수집 및 이용에 대한 내용을 담고 있습니다. KidsWallet은 사용자의 신용 상태를 평가하고, 신용 제공 여부를 결정하기 위해 신용평가사와 협력합니다.
        KidsWallet은 본 약관에 따라 수집된 신용정보를 엄격히 관리하며, 사용자의 동의 없이 제3자에게 제공하지 않습니다. 다만, 금융 거래법에 따른 법적 요청이 있을 경우 관련 기관에 제공될 수 있습니다.
        사용자는 신용정보 제공에 대해 언제든지 철회할 수 있으며, 철회 시 일부 서비스가 제한될 수 있습니다.
                 """.trimIndent()
            )) { index, (title, description) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE9F8FE)),
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clickable {
                                    expandedStates[index] = !expandedStates[index]
                                }
                        ) {
                            Checkbox(
                                checked = checkedStates[index],
                                onCheckedChange = { checked ->
                                    checkedStates[index] = checked
                                    allChecked = checkedStates.all { itChecked -> itChecked }
                                },
                                colors = CheckboxDefaults.colors(checkedColor = Color(0xFF6DCEF5))
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(color = Color.Red)) {
                                        append("(필수) ")
                                    }
                                    append(title)
                                },
                                fontWeight = FontWeight.Bold,
                                style = FontSizes.h16
                            )

                            Spacer(Modifier.weight(1f))
                            Icon(
                                imageVector = if (expandedStates[index]) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }

                        
                        if (expandedStates[index]) {
                            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                                Divider(color = Color(0xFF8C8595))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = description,
                                    style = FontSizes.h12
                                )
                            }
                        }
                    }
                }
            }
        }

        
        BlueButton(
            onClick = {
                if (checkedStates.all { itChecked -> itChecked }) {
                    if (userId != null) {
                        makeAccountViewModel.registerAccount(userId) {
                            isAccountRegistered = true
                        }
                    }
                }
            },
            height = 50,
            text = "동의 후 개설하기",
            enabled = checkedStates.all { itChecked -> itChecked },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )

    }
}



@Composable
fun BlueButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    height: Int = 50,
    elevation: Int = 8,
    textStyle: TextStyle = FontSizes.h16,
    fontWeight: FontWeight = FontWeight.Bold,
    enabled: Boolean = true
) {
    val backgroundBrush = if (enabled) {
        Brush.linearGradient(colors = listOf(Color(0xFF99DDF8), Color(0xFF6DCEF5)))
    } else {
        Brush.linearGradient(colors = listOf(Color(0xFFF7F7F7), Color(0xFFF7F7F7))) 
    }

    
    val buttonElevation = if (enabled) elevation else 0
    val textColor = if (enabled) Color.White else Color.Black
    val buttonFontWeight = if (enabled) FontWeight.Bold else FontWeight.Normal

    Box(
        modifier = modifier
            .shadow(
                elevation = buttonElevation.dp,
                shape = RoundedCornerShape(45.dp)
            )
            .background(
                brush = backgroundBrush,
                shape = RoundedCornerShape(45.dp)
            )
            .height(height.dp)
            .clickable(enabled = enabled, onClick = onClick), 
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle.copy(fontWeight = buttonFontWeight),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}



@Preview(
    showBackground = true,
    device = "spec:width=1440px,height=3120px,dpi=560",
    showSystemUi = true
)
@Composable
fun MakeAccountScreenPreview() {
    MakeAccountScreen(navController = rememberNavController())
}
