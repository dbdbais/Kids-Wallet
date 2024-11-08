package com.ssafy.kidswallet

import MyWalletScreen
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ssafy.kidswallet.ui.screens.begging.BeggingMissionCheckScreen
import com.ssafy.kidswallet.ui.screens.begging.BeggingMissionCompleteScreen
import com.ssafy.kidswallet.ui.screens.begging.BeggingMissionPlayScreen

import com.ssafy.kidswallet.ui.screens.begging.BeggingMoneyScreen
import com.ssafy.kidswallet.ui.screens.begging.BeggingRequestCompleteScreen
import com.ssafy.kidswallet.ui.screens.begging.BeggingRequestReasonScreen
import com.ssafy.kidswallet.ui.screens.begging.BeggingRequestScreen
import com.ssafy.kidswallet.ui.theme.KidsWalletTheme
import com.ssafy.kidswallet.ui.screens.begging.BeggingScreen
import com.ssafy.kidswallet.ui.screens.card.CardScreen
import com.ssafy.kidswallet.ui.screens.login.Login
import com.ssafy.kidswallet.ui.screens.main.MainPageScreen
import com.ssafy.kidswallet.ui.screens.run.RunScreen
import com.ssafy.kidswallet.ui.screens.run.parents.RunParentsDetailScreen
import com.ssafy.kidswallet.ui.screens.run.parents.RunParentsFinishDetailScreen
import com.ssafy.kidswallet.ui.screens.run.parents.RunParentsFinishScreen
import com.ssafy.kidswallet.ui.screens.run.parents.RunParentsMemberListScreen
import com.ssafy.kidswallet.ui.screens.run.parents.RunParentsMoneyScreen
import com.ssafy.kidswallet.ui.screens.run.parents.RunParentsRegisterScreen
import com.ssafy.kidswallet.ui.screens.run.parents.RunParentsScreen
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
        // run
        composable("run") { RunScreen(navController) }
        composable("runParentsFinish") { RunParentsFinishScreen(navController) }
        composable("runParentsFinishDetail") { RunParentsFinishDetailScreen(navController) }
        composable("runParents") { RunParentsScreen(navController) }
        composable("runParentsMoney") { RunParentsMoneyScreen(navController) }
        composable("runParentsDetail") { RunParentsDetailScreen(navController) }
        composable("runParentsMemberList") { RunParentsMemberListScreen(navController) }
        composable("runParentsRegister") { RunParentsRegisterScreen(navController) }
        composable("begging") { BeggingScreen(navController) }

        composable("beggingMissionPlay/{name}/{money}/{begContent}/{missionContent}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val begMoneyString = backStackEntry.arguments?.getString("money") ?: "0"
            val begMoney = begMoneyString.toIntOrNull() ?: 0
            val begContent = backStackEntry.arguments?.getString("begContent") ?: ""
            val missionContent = backStackEntry.arguments?.getString("missionContent") ?: ""
            BeggingMissionPlayScreen(navController, name, begMoney, begContent, missionContent)
        }

        composable("beggingMissionCheck") { BeggingMissionCheckScreen(navController) }
        composable("beggingMissionComplete") { BeggingMissionCompleteScreen(navController) }
        composable("beggingMoney") { BeggingMoneyScreen(navController) }

        composable(
            route = "beggingRequest?name={name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            BeggingRequestScreen(navController, name)
        }

        composable(
            route = "beggingRequestReason?name={name}&amount={amount}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("amount") { type = NavType.IntType}
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            val amount = backStackEntry.arguments?.getInt("amount") ?: 0
            BeggingRequestReasonScreen(navController, name, amount)
        }

        composable(
            route = "beggingRequestComplete?name={name}&amount={amount}&reason={reason}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("amount") { type = NavType.IntType },
                navArgument("reason") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            val amount = backStackEntry.arguments?.getInt("amount") ?: 0
            val reason = backStackEntry.arguments?.getString("reason")

            BeggingRequestCompleteScreen(navController, name, amount, reason)
        }

        composable("quiz") { QuizScreen(navController) }
    }
}
