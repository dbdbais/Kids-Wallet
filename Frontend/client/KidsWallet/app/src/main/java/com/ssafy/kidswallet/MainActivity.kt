package com.ssafy.kidswallet
import LoginRoutingScreen
import QuizScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.ui.screens.run.RunParentsScreen
import com.ssafy.kidswallet.ui.theme.KidsWalletTheme
import com.ssafy.kidswallet.ui.screens.begging.BeggingScreen
import com.ssafy.kidswallet.ui.screens.card.CardScreen
import com.ssafy.kidswallet.ui.screens.main.MainPageScreen
import com.ssafy.kidswallet.ui.screens.mywallet.MyWalletScreen


class MainActivity : ComponentActivity() {
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
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { navController.navigate("loginRouting") },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("로그인")
                }
                Button(
                    onClick = { navController.navigate("mainPage") },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("메인페이지")
                }
                Button(
                    onClick = { navController.navigate("myWallet") },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("내지갑")
                }
                Button(
                    onClick = { navController.navigate("card") },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("카드신청")
                }
                Button(
                    onClick = { navController.navigate("runParents") },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("행복달리기")
                }
                Button(
                    onClick = { navController.navigate("begging") },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("용돈 조르기")
                }
                Button(
                    onClick = { navController.navigate("quiz") },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("퀴즈")
                }
            }
        }

        composable("loginRouting") { LoginRoutingScreen(navController) }
        composable("mainPage") { MainPageScreen(navController) }
        composable("myWallet") { MyWalletScreen(navController) }
        composable("card") { CardScreen(navController) }
        composable("runParents") { RunParentsScreen(navController) }
        composable("begging") { BeggingScreen(navController) }
        composable("quiz") { QuizScreen(navController) }
    }
}
