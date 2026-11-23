package org.sopt.play.register

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

// 회원가입 Route = ViewModel이랑 RegisterScreen을 이어주는 "연결 담당"
// ViewModel 가져오기, 상태 구독, SideEffect(토스트/이동) 처리는 전부 여기서 함
@Composable
fun RegisterRoute(
    onRegisterSuccess: (String, String) -> Unit,
    // 가입 성공하면 할 일 (NavHost가 정함 → 가입 정보 저장 + 로그인으로 돌아가기)
    // (String, String) = 이메일, 비밀번호 2개를 넘겨줌
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = viewModel(),
    // ViewModelProvider를 통해 RegisterViewModel 받아오기 (직접 생성 X)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    // uiState 구독 → 값 바뀌면 리컴포지션 (백그라운드면 구독 멈춤)

    val context = LocalContext.current // 토스트에 필요
    val lifecycleOwner = LocalLifecycleOwner.current // 화면 생명주기 주인

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            // 화면이 보이는 동안만 SideEffect 받기 (uiState랑 똑같이 생명주기에 맞춤)
            .collect { sideEffect ->
                when (sideEffect) {
                    is RegisterSideEffect.ShowToast -> {
                        Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                    }

                    is RegisterSideEffect.NavigateToLogin -> {
                        onRegisterSuccess(sideEffect.email, sideEffect.password)
                        // 가입 성공 → 밖(NavHost)에 가입 정보 전달 → NavHost가 저장하고 popBackStack
                        // 둘 다 data class라서 is로 타입 확인 후 안의 값 꺼냄
                    }
                }
            }
    }

    RegisterScreen(
        uiState = uiState, // 화면에 그릴 상태
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onPasswordConfirmChange = viewModel::onPasswordConfirmChange,
        // 입력칸 4개 → 글자 바뀌면 각각 ViewModel 함수 호출
        onRegisterClick = viewModel::onRegisterClick,
        // 회원가입 버튼 → ViewModel이 토스트 + 이동 명령을 보냄
        // 매개변수 없는 함수라서 { } 대신 함수 참조(::)로 바로 넘김
        modifier = modifier,
    )
}
