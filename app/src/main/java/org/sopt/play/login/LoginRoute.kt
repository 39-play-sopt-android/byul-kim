package org.sopt.play.login

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

// Route = ViewModel이랑 Screen을 이어주는 "연결 담당"
// ViewModel 가져오기, 상태 구독, SideEffect(토스트/이동) 처리는 전부 여기서 함
// LoginScreen은 ViewModel을 모르고 받은 값으로 그리기만 함
// → 프리뷰에서 진짜 ViewModel 없이 가짜 상태만 넣어서 그려볼 수 있음
@Composable
fun LoginRoute(
    registeredEmail: String, // 회원가입한 이메일 (NavHost가 줌)
    registeredPassword: String, // 회원가입한 비밀번호 (NavHost가 줌)
    onLoginSuccess: () -> Unit, // 로그인 성공하면 할 일 (NavHost가 정함 → 홈으로 이동)
    onRegisterClick: () -> Unit, // 회원가입하기 누르면 할 일 (NavHost가 정함 → 회원가입으로 이동)
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
    // viewModel() = ViewModelProvider를 통해 LoginViewModel을 받아옴
    // 이미 만들어진 게 있으면 그걸 주고, 없으면 새로 만들어줌 → 화면 회전해도 같은 ViewModel 유지
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    // ViewModel의 uiState(StateFlow)를 구독해서 Compose State로 바꿈
    // collectAsStateWithLifecycle = 앱이 백그라운드로 가면 구독을 멈추고, 돌아오면 다시 구독

    val context = LocalContext.current // 토스트에 필요
    val lifecycleOwner = LocalLifecycleOwner.current
    // 지금 화면의 생명주기(화면에 보이는지, 백그라운드인지)를 알려주는 주인

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        // key에 sideEffect랑 lifecycleOwner를 넣음 → 둘 중 하나가 바뀌면 다시 시작
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            // 화면이 보이는 동안(STARTED 이상)만 받고, 백그라운드로 가면 받기를 멈춤
            // → uiState랑 똑같이 생명주기에 맞춰서 수집 (jisang 리뷰 반영)
            .collect { sideEffect ->
                when (sideEffect) {
                    // sealed interface라서 종류가 정해져 있음 → 하나라도 빠뜨리면 컴파일 에러
                    LoginSideEffect.NavigateToHome -> onLoginSuccess()
                    // 홈 이동 → 밖(NavHost)에 성공 알리기 → NavHost가 Home으로 navigate

                    is LoginSideEffect.ShowToast -> {
                        Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                        // is = 이 종류야? 확인 → ShowToast는 class라서 is로 확인해야 message를 꺼낼 수 있음
                    }
                }
            }
    }

    LoginScreen(
        uiState = uiState, // 화면에 그릴 상태
        onEmailChange = viewModel::onEmailChange, // 이메일 바뀌면 ViewModel 함수 호출
        onPasswordChange = viewModel::onPasswordChange, // 비밀번호 바뀌면 ViewModel 함수 호출
        onLoginClick = { viewModel.onLoginClick(registeredEmail, registeredPassword) },
        // 로그인 버튼 → 회원가입 정보를 같이 넘겨서 ViewModel이 성공/실패 판단
        // 매개변수가 있어서 함수 참조(::) 대신 { } 람다로 감쌈
        onRegisterClick = onRegisterClick, // 화면 이동만 하는 거라 ViewModel 안 거치고 그대로 넘김
        modifier = modifier,
    )
}
