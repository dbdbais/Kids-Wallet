package com.ssafy.kidswallet

import android.os.Build
import QuizScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.ui.screens.begging.BeggingMissionScreen
import com.ssafy.kidswallet.ui.screens.begging.BeggingMoneyScreen
import com.ssafy.kidswallet.ui.screens.begging.BeggingRequestScreen
import com.ssafy.kidswallet.ui.screens.run.RunParentsScreen
import com.ssafy.kidswallet.ui.theme.KidsWalletTheme
import com.ssafy.kidswallet.ui.screens.begging.BeggingScreen
import com.ssafy.kidswallet.ui.screens.card.CardScreen
import com.ssafy.kidswallet.ui.screens.login.Login
import com.ssafy.kidswallet.ui.screens.main.MainPageScreen
import com.ssafy.kidswallet.ui.screens.mywallet.MyWalletScreen
import com.ssafy.kidswallet.ui.screens.signup.SignUp

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KidsWalletTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController() // NavController 생성
                    MainScreen(navController) // NavController를 MainScreen에 전달
                }
            }
        }
    }
}


@Composable
fun MainScreen(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "loginRouting") {
        composable("main") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { navController.navigate("card") },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("카드신청")
                }
            }
        }

        composable("loginRouting") { Login(navController) }
        composable("signup") { SignUp(navController) }
        composable("mainPage") { MainPageScreen(navController) }
        composable("myWallet") { MyWalletScreen(navController) }
        composable("card") { CardScreen(navController) }
        composable("runParents") { RunParentsScreen(navController) }
        composable("begging") { BeggingScreen(navController) }
        composable("beggingMission") { BeggingMissionScreen(navController) }
        composable("beggingMoney") { BeggingMoneyScreen(navController) }
        composable("beggingRequest") { BeggingRequestScreen(navController) }
        composable("quiz") { QuizScreen(navController) }
    }
}
