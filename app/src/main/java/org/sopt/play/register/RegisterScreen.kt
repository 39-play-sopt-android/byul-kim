package org.sopt.play.register

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.component.SoptTextField
import org.sopt.play.ui.theme.B28
import org.sopt.play.ui.theme.Black
import org.sopt.play.ui.theme.Gray1
import org.sopt.play.ui.theme.Gray3
import org.sopt.play.ui.theme.PlaySoptTheme
import org.sopt.play.ui.theme.Sb14

// 회원가입 화면 "그리기 담당"
// ViewModel을 모르고, 받은 uiState로 그리기만 함
// 입력이나 클릭이 생기면 받은 함수를 불러서 밖(Route)에 알리기만 함
@Composable
fun RegisterScreen(
    uiState: RegisterUiState, // 화면에 그릴 상태 (Route가 줌)
    onNameChange: (String) -> Unit, // 이름 칸 글자 바뀌면 할 일
    onEmailChange: (String) -> Unit, // 이메일 칸 글자 바뀌면 할 일
    onPasswordChange: (String) -> Unit, // 비밀번호 칸 글자 바뀌면 할 일
    onPasswordConfirmChange: (String) -> Unit, // 비밀번호 확인 칸 글자 바뀌면 할 일
    onRegisterClick: () -> Unit, // 회원가입 버튼 누르면 할 일
    modifier: Modifier = Modifier,
) {
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
            onValueChange = onNameChange, // 받은 함수 호출 (실제로는 ViewModel 함수)
            placeholder = "홍길동"
        )

        Spacer(modifier = Modifier.height(32.dp))

        SoptTextField(
            label = "이메일 주소",
            value = uiState.email,
            onValueChange = onEmailChange,
            placeholder = "abc@email.com",
            isError = uiState.isEmailError,
            errorMessage = "올바른 이메일을 입력해주세요."
        )

        Spacer(modifier = Modifier.height(32.dp))

        SoptTextField(
            label = "비밀번호",
            value = uiState.password,
            onValueChange = onPasswordChange,
            placeholder = "6자 이상의 비밀번호",
            isPassword = true,
            isError = uiState.isPasswordError,
            errorMessage = "비밀번호는 6자 이상 입력해주세요."
        )

        Spacer(modifier = Modifier.height(32.dp))

        SoptTextField(
            label = "비밀번호 확인",
            value = uiState.passwordConfirm,
            onValueChange = onPasswordConfirmChange,
            placeholder = "6자 이상의 비밀번호",
            isPassword = true,
            isError = uiState.isPasswordConfirmError,
            errorMessage = "비밀번호와 동일하게 입력해주세요."
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onRegisterClick, // 가입 처리는 Route → ViewModel에 맡김
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

// 처음 들어왔을 때 (빈 칸, 버튼 꺼짐)
@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    PlaySoptTheme {
        RegisterScreen(
            uiState = RegisterUiState(), // 가짜 상태: 아무것도 안 쓴 상태
            onNameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onPasswordConfirmChange = {},
            onRegisterClick = {},
        )
    }
}

// 다 맞게 입력한 상태 (버튼 켜짐)
@Preview(showBackground = true)
@Composable
private fun RegisterScreenFilledPreview() {
    PlaySoptTheme {
        RegisterScreen(
            uiState = RegisterUiState(
                name = "김솝트",
                email = "sopt@sopt.org",
                password = "123456",
                passwordConfirm = "123456",
            ),
            // 4칸 다 입력 + 에러 없음 → isRegisterEnabled = true → 버튼 검정
            onNameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onPasswordConfirmChange = {},
            onRegisterClick = {},
        )
    }
}
