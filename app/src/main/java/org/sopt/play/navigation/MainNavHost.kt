package org.sopt.play.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.sopt.play.HomeScreen
import org.sopt.play.LoginScreen
import org.sopt.play.RegisterScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

// 1주차엔 화면마다 Activity(집)가 따로 있었는데, 이제는 MainActivity 하나 안에서
// 이 NavHost가 화면(Login, Register, Home)을 바꿔 끼워줌 → SAA
// 화면끼리 어떻게 이동하는지가 전부 이 파일 한 곳에 모여 있어서 앱 흐름을 한눈에 볼 수 있음
@Composable
fun MainNavHost(modifier: Modifier = Modifier) {
    // ① 화면 이동 담당
    // navController: 화면 이동 담당 (navigate = 가기, popBackStack = 뒤로)
    // 이동 기록(백스택)도 들고 있음
    // rememberNavController()에 remember 내장 → 다시 그려져도 기록 유지
    val navController = rememberNavController()

    // ② 회원가입 정보 (= 거실에 둔 짐) → Login, Register 둘 다 써야 해서 여기에
    var registeredEmail by remember { mutableStateOf("") }
    var registeredPassword by remember { mutableStateOf("") }

    // ③ NavHost = 화면이 실제로 그려지는 공간
    // 현재 화면 하나를 보여주고, navigate하면 다른 화면으로 바꿔 끼움
    NavHost(
        navController = navController,
        startDestination = Login,          // 앱 켜면 처음 보일 화면
        modifier = modifier,
    ) { // composable<Route> { 화면 } 형태 = React Router의 <Route path element />
        // Login 화면
        composable<Login> {
            LoginScreen(
                registeredEmail = registeredEmail,           // 짐 내려주기
                registeredPassword = registeredPassword,
                onLoginSuccess = {                           // "성공!" 들으면
                    navController.navigate(Home) {           // Home으로 이동하고
                        popUpTo<Login> { inclusive = true }  // Login 기록 지우기
                    }
                },
                onRegisterClick = {                          // "회원가입!" 들으면
                    navController.navigate(Register)         // Register로 이동
                },
            )
        }

        // Register 화면
        composable<Register> {
            RegisterScreen(
                // RegisterScreen이 가입 버튼 누르면 이메일, 비번을 위로 올려보냄
                onRegisterClick = { email, password ->       // 가입 정보 받으면
                    registeredEmail = email                  // 거실에 짐 저장
                    registeredPassword = password
                    navController.popBackStack()             // 뒤로 = Login으로
                },
            )
        }

        // Home 화면
        composable<Home> {
            HomeScreen()
        }
    }
}