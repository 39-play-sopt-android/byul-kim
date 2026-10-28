package org.sopt.play

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.play.component.SoptTextField
import org.sopt.play.ui.theme.B28
import org.sopt.play.ui.theme.Black
import org.sopt.play.ui.theme.Gray1
import org.sopt.play.ui.theme.Gray3
import org.sopt.play.ui.theme.PlaySoptTheme
import org.sopt.play.ui.theme.Sb14

@Composable
fun RegisterScreen(
    onRegisterClick: (String, String) -> Unit,
    //onRegisterClick 가입 완료되면 할 일을 밖에서 받음. (String, String) 이메일, 비밀번호 2개를 넘겨줌
    // -> Unit 돌려주는건 없음
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = viewModel(),
    // ViewModelProvider를 통해 RegisterViewModel 받아오기 (직접 생성 X)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    // uiState 구독 → 값 바뀌면 리컴포지션
    // rememberSaveable 4개를 대신함 (ViewModel이 화면 회전에도 상태 유지해줌)

    val context = LocalContext.current // 토스트에 필요

    LaunchedEffect(Unit) {
        // 컴포지션될 때 한 번 시작해서 sideEffect 구독
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is RegisterSideEffect.ShowToast -> {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                }

                is RegisterSideEffect.NavigateToLogin -> {
                    onRegisterClick(sideEffect.email, sideEffect.password)
                    // 밖(NavHost)에 가입 정보 전달 → NavHost가 저장하고 popBackStack
                    // 둘 다 data class라서 is로 타입 확인 후 안의 값 꺼냄
                }
            }
        }
    }

    Column( //세로 배치
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(top = 60.dp)
    ) {
        Text(
            text = "이메일로 회원가입",
            style = B28,
            color = Black
        )

        Spacer(modifier = Modifier.height(40.dp))

        SoptTextField(
            label = "이름",
            value = uiState.name, // uiState에서 가져옴
            onValueChange = viewModel::onNameChange, // ViewModel 함수 호출
            placeholder = "홍길동"
        )

        Spacer(modifier = Modifier.height(32.dp))

        SoptTextField(
            label = "이메일 주소",
            value = uiState.email,
            onValueChange = viewModel::onEmailChange,
            placeholder = "abc@email.com",
            isError = uiState.isEmailError,
            errorMessage = "올바른 이메일을 입력해주세요."
        )

        Spacer(modifier = Modifier.height(32.dp))

        SoptTextField(
            label = "비밀번호",
            value = uiState.password,
            onValueChange = viewModel::onPasswordChange,
            placeholder = "6자 이상의 비밀번호",
            isPassword = true,
            isError = uiState.isPasswordError,
            errorMessage = "비밀번호는 6자 이상 입력해주세요."
        )

        Spacer(modifier = Modifier.height(32.dp))

        SoptTextField(
            label = "비밀번호 확인",
            value = uiState.passwordConfirm,
            onValueChange = viewModel::onPasswordConfirmChange,
            placeholder = "6자 이상의 비밀번호",
            isPassword = true,
            isError = uiState.isPasswordConfirmError,
            errorMessage = "비밀번호와 동일하게 입력해주세요."
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = viewModel::onRegisterClick,
            // ViewModel에 클릭 이벤트 전달 (onRegisterClick(email, password) 직접 호출)
            // 매개변수 없는 함수라서 { viewModel.onRegisterClick() } 대신 함수 참조로 바로 넘김
            enabled = uiState.isRegisterEnabled, // uiState에서 가져옴
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Black,
                contentColor = Gray1,
                disabledContainerColor = Gray1,
                disabledContentColor = Gray3
            ),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "회원가입", style = Sb14)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    PlaySoptTheme {
        RegisterScreen(onRegisterClick = { _, _ -> })
    }
}