package org.sopt.play.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.sopt.play.home.HomeRoute
import org.sopt.play.login.LoginRoute
import org.sopt.play.post.PostRoute
import org.sopt.play.register.RegisterRoute

@Composable
fun MainNavHost(modifier: Modifier = Modifier) {
    // ① 화면 이동 담당
    // navController: 화면 이동 담당 (navigate = 가기, popBackStack = 뒤로)
    // 이동 기록(백스택)도 들고 있음
    // rememberNavController()에 remember 내장 → 다시 그려져도 기록 유지
    val navController = rememberNavController()

    // ② 회원가입 정보
    // 회원가입 화면은 가입 끝나면 닫혀서(popBackStack) 그 안의 값도 사라짐
    // → 화면들보다 오래 사는 NavHost에 올려두고, 필요한 화면에 내려줌
    var registeredName by remember { mutableStateOf("") }
    var registeredEmail by remember { mutableStateOf("") }
    var registeredPassword by remember { mutableStateOf("") }

    // ③ NavHost = 화면이 실제로 그려지는 공간
    // 현재 화면 하나를 보여주고, navigate하면 다른 화면으로 바꿔 끼움
    NavHost(
        navController = navController,
        startDestination = Login, // 앱 켜면 처음 보일 화면
        modifier = modifier,
    ) { // composable<Route> { 화면 } 형태 = React Router의 <Route path element />
        // Login 화면
        composable<Login> {
            LoginRoute(
                registeredEmail = registeredEmail,
                registeredPassword = registeredPassword,
                onLoginSuccess = {
                    navController.navigate(Home) { // Home으로 이동하고
                        popUpTo<Login> { inclusive = true } // Login 기록 지우기
                    }
                },
                onRegisterClick = {
                    navController.navigate(Register) // Register로 이동
                },
            )
        }

        // Register 화면
        composable<Register> {
            RegisterRoute(
                // RegisterRoute가 가입 성공하면 이름, 이메일, 비번을 위로 올려보냄
                onRegisterSuccess = { name, email, password -> // 가입 성공 정보 3개 받으면
                    registeredName = name
                    registeredEmail = email
                    registeredPassword = password
                    navController.popBackStack() // 뒤로 = Login으로 (이때 회원가입 화면 + ViewModel 사라짐)
                },
            )
        }

        // Home 화면
        composable<Home> {
            HomeRoute(
                onSearchClick = {
                    navController.navigate(Post)
                },
            )
        }

        // Post 화면 (게시글)
        composable<Post> {
            PostRoute(
                author = registeredName,
            )
        }
    }
}
