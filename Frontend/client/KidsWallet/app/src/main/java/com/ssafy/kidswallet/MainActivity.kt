package com.ssafy.kidswallet

import android.os.Build
import QuizScreen
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.kidswallet.ui.screens.alert.AlertListScreen
import com.ssafy.kidswallet.ui.screens.begging.mission.BeggingMissionCheckScreen
import com.ssafy.kidswallet.ui.screens.begging.mission.BeggingMissionCompleteScreen
import com.ssafy.kidswallet.ui.screens.begging.mission.BeggingMissionPlayScreen

import com.ssafy.kidswallet.ui.screens.begging.beggingmoney.BeggingMoneyScreen
import com.ssafy.kidswallet.ui.screens.begging.beggingmoney.BeggingRequestCompleteScreen
import com.ssafy.kidswallet.ui.screens.begging.beggingmoney.BeggingRequestReasonScreen
import com.ssafy.kidswallet.ui.screens.begging.beggingmoney.BeggingRequestScreen
import com.ssafy.kidswallet.ui.theme.KidsWalletTheme
import com.ssafy.kidswallet.ui.screens.begging.BeggingScreen
import com.ssafy.kidswallet.ui.screens.begginglist.assignmission.ParentBeggingAssignMissionScreen
import com.ssafy.kidswallet.ui.screens.begginglist.assignmission.ParentBeggingCompleteMissionScreen
import com.ssafy.kidswallet.ui.screens.begginglist.ParentBeggingCompleteScreen
import com.ssafy.kidswallet.ui.screens.begginglist.assignmission.ParentBeggingRequestCheckScreen
import com.ssafy.kidswallet.ui.screens.begginglist.ParentBeggingWaitingScreen
import com.ssafy.kidswallet.ui.screens.begginglist.testmission.ParentBeggingTestCompleteScreen
import com.ssafy.kidswallet.ui.screens.begginglist.testmission.ParentBeggingTestMissionScreen
import com.ssafy.kidswallet.ui.screens.card.Card2Screen
import com.ssafy.kidswallet.ui.screens.card.Card3Screen
import com.ssafy.kidswallet.ui.screens.card.CardScreen
import com.ssafy.kidswallet.ui.screens.login.Login
import com.ssafy.kidswallet.ui.screens.main.MainPageScreen
import com.ssafy.kidswallet.ui.screens.makeaccount.ConnectAccountScreen
import com.ssafy.kidswallet.ui.screens.makeaccount.MakeAccountScreen
import com.ssafy.kidswallet.ui.screens.makeaccount.MyDataScreen
import com.ssafy.kidswallet.ui.screens.mywallet.MyWalletDepositScreen
import com.ssafy.kidswallet.ui.screens.mywallet.MyWalletManageScreen
import com.ssafy.kidswallet.ui.screens.mywallet.MyWalletScreen
import com.ssafy.kidswallet.ui.screens.regularlist.RegularListScreen
import com.ssafy.kidswallet.ui.screens.mywallet.MyWalletTransferScreen
import com.ssafy.kidswallet.ui.screens.mywallet.MyWalletWithdrawScreen
import com.ssafy.kidswallet.ui.screens.run.RunScreen
import com.ssafy.kidswallet.ui.screens.run.others.RunOthersScreen
import com.ssafy.kidswallet.ui.screens.run.parents.ParentsDetailScreen
import com.ssafy.kidswallet.ui.screens.run.parents.RunParentsDetailScreen
import com.ssafy.kidswallet.ui.screens.run.parents.RunParentsFinishDetailScreen
import com.ssafy.kidswallet.ui.screens.run.parents.RunParentsFinishScreen
import com.ssafy.kidswallet.ui.screens.run.parents.RunParentsMemberListScreen
import com.ssafy.kidswallet.ui.screens.run.parents.RunParentsMoneyScreen
import com.ssafy.kidswallet.ui.screens.run.parents.RunParentsRegisterScreen
import com.ssafy.kidswallet.ui.screens.run.parents.RunParentsScreen
import com.ssafy.kidswallet.ui.screens.run.viewmodel.state.StateRunViewModel
import com.ssafy.kidswallet.ui.screens.signup.SignUp
import com.ssafy.kidswallet.ui.splash.SplashScreen
import com.ssafy.kidswallet.viewmodel.TogetherDetailViewModel
import com.ssafy.kidswallet.viewmodel.state.StateBeggingMissionViewModel
import com.ssafy.kidswallet.viewmodel.state.StateRunMoneyViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "FcmToken") // FcmToken을 위한 DataStore 인스턴스 생성

    class MainActivity : ComponentActivity() {
        private val stateRunViewModel: StateRunViewModel by viewModels()
        private val stateBeggingMissionViewModel: StateBeggingMissionViewModel by viewModels()
        private val stateRunMoneyViewModel: StateRunMoneyViewModel by viewModels()
        private val togetherDetailViewModel: TogetherDetailViewModel by viewModels()
        @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        //fcm
        //TODO: FCM
        // Firebase 초기화
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // FCM 토큰을 DataStore에 저장
            val fcmTokenKey = stringPreferencesKey("fcm_token")
            lifecycleScope.launch {
                applicationContext.dataStore.edit { preferences ->
                    preferences[fcmTokenKey] = token
                }

                // DataStore에서 FCM 토큰을 읽어서 로그로 출력
                val savedToken = applicationContext.dataStore.data.first()[fcmTokenKey]
                Log.d(ContentValues.TAG, "Saved FCM Token in DataStore: $savedToken")
            }

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(ContentValues.TAG, msg)
        }) //FCM end

        setContent {
            KidsWalletTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MainScreen(navController, stateRunViewModel, stateBeggingMissionViewModel, stateRunMoneyViewModel = stateRunMoneyViewModel)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun MainScreen(
        navController: NavHostController,
        stateRunViewModel: StateRunViewModel,
        stateBeggingMissionViewModel: StateBeggingMissionViewModel,
        stateRunMoneyViewModel: StateRunMoneyViewModel
    ) {
        NavHost(navController = navController, startDestination = "splash") {
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

            composable("splash") { SplashScreen(navController) }
            composable("loginRouting") { Login(navController) }
            composable("signup") { SignUp(navController) }
            composable("mainPage") { MainPageScreen(navController) }
            composable("card") { CardScreen(navController) }
            composable("card2") { Card2Screen(navController) }
            composable("card3") { Card3Screen(navController) }
            // run
            composable("run") { RunScreen(navController) }
            composable(
                route = "parentsDetail/{togetherRunId}",
                arguments = listOf(navArgument("togetherRunId") { type = NavType.IntType })
            ) { backStackEntry ->
                val togetherRunId = backStackEntry.arguments?.getInt("togetherRunId")
                ParentsDetailScreen(
                    navController = navController,
                    togetherRunId = togetherRunId,
                    togetherDetailViewModel = togetherDetailViewModel // viewModel 전달
                )
            }
            composable("runOthers") { RunOthersScreen(navController) }
            composable("runParentsFinish") { RunParentsFinishScreen(navController) }
            composable("runParentsFinishDetail") { RunParentsFinishDetailScreen(navController) }
            composable("runParents") {
                RunParentsScreen(navController = navController, viewModel = stateRunViewModel)
            }
            composable("runParentsMoney") {
                RunParentsMoneyScreen(navController = navController, stateRunMoneyViewModel = stateRunMoneyViewModel)
            }
            composable(
                route = "runParentsDetail/{togetherRunId}",
                arguments = listOf(navArgument("togetherRunId") { type = NavType.IntType })
            ) { backStackEntry ->
                val togetherRunId = backStackEntry.arguments?.getInt("togetherRunId")
                RunParentsDetailScreen(
                    navController = navController,
                    togetherRunId = togetherRunId,
                    togetherDetailViewModel = togetherDetailViewModel // viewModel 전달
                )
            }



            composable("runParentsMemberList") { RunParentsMemberListScreen(navController) }
            composable("runParentsRegister") {
                RunParentsRegisterScreen(navController = navController, viewModel = stateRunViewModel, stateRunMoneyViewModel = stateRunMoneyViewModel)
            }

            // wallet
            composable("myWallet") { MyWalletScreen(navController) }
            composable("myWalletManage") { MyWalletManageScreen(navController) }
            composable("myWalletTransfer") { MyWalletTransferScreen(navController) }
            composable("myWalletDeposit") { MyWalletDepositScreen(navController) }
            composable("myWalletWithdraw") { MyWalletWithdrawScreen(navController) }

            // begging
            composable("begging") { BeggingScreen(navController) }

            composable("beggingMissionPlay/{id}/{name}/{money}/{begContent}/{missionContent}") { backStackEntry ->
                val idString = backStackEntry.arguments?.getString("id") ?: "0"
                val id = idString.toIntOrNull() ?: 0
                val name = backStackEntry.arguments?.getString("name") ?: ""
                val begMoneyString = backStackEntry.arguments?.getString("money") ?: "0"
                val begMoney = begMoneyString.toIntOrNull() ?: 0
                val begContent = backStackEntry.arguments?.getString("begContent") ?: ""
                val missionContent = backStackEntry.arguments?.getString("missionContent") ?: ""
                BeggingMissionPlayScreen(navController, id, name, begMoney, begContent, missionContent)
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
                    navArgument("amount") { type = NavType.IntType }
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

            composable("parentBeggingWaiting") { ParentBeggingWaitingScreen(navController, viewModel = stateBeggingMissionViewModel) }
            composable("parentBeggingComplete") { ParentBeggingCompleteScreen(navController) }
            composable("parentBeggingRequestCheck/{id}/{name}/{begMoney}/{begContent}") { backStackEntry ->
                val idString = backStackEntry.arguments?.getString("id") ?: "0"
                val id = idString.toIntOrNull() ?: 0
                val name = backStackEntry.arguments?.getString("name") ?: ""
                val begMoneyString = backStackEntry.arguments?.getString("begMoney") ?: "0"
                val begMoney = begMoneyString.toIntOrNull() ?: 0
                val begContent = backStackEntry.arguments?.getString("begContent") ?: ""
                ParentBeggingRequestCheckScreen(navController, id, name, begMoney, begContent)
            }

            composable("parentBeggingAssignMission/{id}/{name}/{begMoney}/{begContent}") { backStackEntry ->
                val idString = backStackEntry.arguments?.getString("id") ?: "0"
                val id = idString.toIntOrNull() ?: 0
                val name = backStackEntry.arguments?.getString("name") ?: ""
                val begMoneyString = backStackEntry.arguments?.getString("begMoney") ?: "0"
                val begMoney = begMoneyString.toIntOrNull() ?: 0
                val begContent = backStackEntry.arguments?.getString("begContent") ?: ""
                ParentBeggingAssignMissionScreen(navController, id, name, begMoney, begContent)
            }

            composable("parentBeggingCompleteMission/{id}/{name}/{begMoney}/{begContent}/{reason}") { backStackEntry ->
                val idString = backStackEntry.arguments?.getString("id") ?: "0"
                val id = idString.toIntOrNull() ?: 0
                val name = backStackEntry.arguments?.getString("name") ?: ""
                val begMoneyString = backStackEntry.arguments?.getString("begMoney") ?: "0"
                val begMoney = begMoneyString.toIntOrNull() ?: 0
                val begContent = backStackEntry.arguments?.getString("begContent") ?: ""
                val reason = backStackEntry.arguments?.getString("reason") ?: ""
                ParentBeggingCompleteMissionScreen(
                    navController,
                    id,
                    name,
                    begMoney,
                    begContent,
                    reason
                )
            }

            composable("parentBeggingTestMission/{id}/{name}/{begMoney}/{begContent}/{missionContent}") { backStackEntry ->
                val idString = backStackEntry.arguments?.getString("id") ?: "0"
                val id = idString.toIntOrNull() ?: 0
                val name = backStackEntry.arguments?.getString("name") ?: ""
                val begMoneyString = backStackEntry.arguments?.getString("begMoney") ?: "0"
                val begMoney = begMoneyString.toIntOrNull() ?: 0
                val begContent = backStackEntry.arguments?.getString("begContent") ?: ""
                val missionContent = backStackEntry.arguments?.getString("missionContent") ?: ""
                ParentBeggingTestMissionScreen(
                    navController,
                    id,
                    name,
                    begMoney,
                    begContent,
                    missionContent,
                    viewModel = stateBeggingMissionViewModel
                )
            }

            composable("parentBeggingTestComplete/{id}/{name}/{begMoney}/{begContent}") { backStackEntry ->
                val idString = backStackEntry.arguments?.getString("id") ?: "0"
                val id = idString.toIntOrNull() ?: 0
                val name = backStackEntry.arguments?.getString("name") ?: ""
                val begMoneyString = backStackEntry.arguments?.getString("begMoney") ?: "0"
                val begMoney = begMoneyString.toIntOrNull() ?: 0
                val begContent = backStackEntry.arguments?.getString("begContent") ?: ""
                ParentBeggingTestCompleteScreen(navController, id, name, begMoney, begContent)
            }

            composable("quiz") { QuizScreen(navController) }

            composable("makeAccount") { MakeAccountScreen(navController) }

            composable("alertList") { AlertListScreen(navController) }

            composable("regularList") { RegularListScreen(navController) }

            composable("myData") { MyDataScreen(navController) }

            composable("connectAccount") { ConnectAccountScreen(navController) }
        }
    }
}
