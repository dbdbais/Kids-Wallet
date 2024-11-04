package com.ssafy.kidswallet
//import QuizScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ssafy.kidswallet.ui.screens.login.Login
import com.ssafy.kidswallet.ui.screens.signup.SignUp
import com.ssafy.kidswallet.ui.theme.KidsWalletTheme

//import com.ssafy.kidswallet.ui.screens.run.parents.RunParentsScreen
//import com.ssafy.kidswallet.ui.theme.KidsWalletTheme
//import com.ssafy.kidswallet.ui.screens.begging.BeggingScreen
//import com.ssafy.kidswallet.ui.screens.card.CardScreen
//import com.ssafy.kidswallet.ui.screens.mywallet.MyWalletScreen


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
                Text(
                    text = "로그인",
                    modifier = Modifier
                        .clickable { navController.navigate("loginRouting") }
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "내지갑",
                    modifier = Modifier
                        .clickable { navController.navigate("myWallet") }
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "카드신청",
                    modifier = Modifier
                        .clickable { navController.navigate("card") }
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "행복달리기",
                    modifier = Modifier
                        .clickable { navController.navigate("runParents") }
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "용돈 조르기",
                    modifier = Modifier
                        .clickable { navController.navigate("begging") }
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "퀴즈 ",
                    modifier = Modifier
                        .clickable { navController.navigate("quize") }
                        .padding(16.dp)
                )
            }
        }

        composable("loginRouting") { Login(navController) }
        composable("signup") {SignUp(navController)}
//        composable("myWallet") { MyWalletScreen(navController) }
//        composable("card") { CardScreen(navController) }
//        composable("runParents") { RunParentsScreen(navController) }
//        composable("begging") { BeggingScreen(navController) }
//        composable("quize") { QuizScreen(navController) }
    }
}