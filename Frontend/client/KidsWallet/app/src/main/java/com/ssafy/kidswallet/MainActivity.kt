package com.ssafy.kidswallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.kidswallet.ui.screens.run.parents.RunParentsScreen
import com.ssafy.kidswallet.ui.theme.KidsWalletTheme
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.ui.screens.begging.BeggingScreen
import com.ssafy.kidswallet.ui.screens.card.CardScreen
import com.ssafy.kidswallet.ui.screens.mywallet.MyWalletScreen
import com.ssafy.kidswallet.ui.screens.quize.QuizScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KidsWalletTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var showMyWalletScreen by remember { mutableStateOf(false) }
    var showCardScreen by remember { mutableStateOf(false) }
    var showRunParentsScreen by remember { mutableStateOf(false) }
    var showBeggingScreen by remember { mutableStateOf(false) }
    var showQuizScreen by remember { mutableStateOf(false) }

    when {
        showMyWalletScreen -> MyWalletScreen()
        showCardScreen -> CardScreen()
        showRunParentsScreen -> RunParentsScreen()
        showBeggingScreen -> BeggingScreen()
        showQuizScreen -> QuizScreen()
        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "내지갑",
                    modifier = Modifier
                        .clickable { showMyWalletScreen = true }
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "카드신청",
                    modifier = Modifier
                        .clickable { showCardScreen = true }
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "행복달리기",
                    modifier = Modifier
                        .clickable { showRunParentsScreen = true }
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "용돈 조르기",
                    modifier = Modifier
                        .clickable { showBeggingScreen = true }
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "퀴즈 ",
                    modifier = Modifier
                        .clickable { showQuizScreen = true }
                        .padding(16.dp)
                )
            }
        }
    }
}