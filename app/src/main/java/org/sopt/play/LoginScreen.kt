package org.sopt.play

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
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
import org.sopt.play.ui.theme.Gray6
import org.sopt.play.ui.theme.M14
import org.sopt.play.ui.theme.PlaySoptTheme
import org.sopt.play.ui.theme.Sb14

//Ctrl + Alt + o 누르면 자동 정리
//Ctrl + Alt + L 으로 자동 정렬

@Composable //@Composable: 화면 그리는 함수 표시
fun LoginScreen(
    registeredEmail: String, // 회원가입한 이메일 (밖에서 받음)
    registeredPassword: String, // 회원가입한 비밀번호 (밖에서 받음)
    onLoginSuccess: () -> Unit, // 로그인 성공하면 할 일 (밖에서 정함)
    onRegisterClick: () -> Unit, // 회원가입하기 누르면 할 일 (밖에서 정함)
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
    // 이 화면에서 쓸 ViewModel
    // viewModel() = ViewModelProvider를 통해 LoginViewModel 인스턴스를 받아오는 함수
    // 이미 만들어진 게 있으면 그걸 주고, 없으면 새로 만들어줌 → 화면 회전해도 같은 ViewModel 유지
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    // ViewModel의 uiState(StateFlow)를 구독해서 Compose State로 바꿈
    // uiState 값이 바뀔 때마다 리컴포지션 → 화면 다시 그림
    // collectAsStateWithLifecycle = 앱이 백그라운드로 가면 구독을 멈추고, 돌아오면 다시 구독
    // by = uiState.value 대신 uiState로 바로 쓰게 해줌

    val context = LocalContext.current //지금 화면 정보 (토스트에 필요)

    LaunchedEffect(Unit) {
        // 화면이 처음 그려질 때 딱 한 번 실행되는 코루틴
        // key가 Unit(안 바뀌는 값)이라서 화면이 살아있는 동안 재시작 안 됨
        viewModel.sideEffect.collect { sideEffect ->
            // ViewModel이 emit한 sideEffect(SharedFlow)를 구독
            when (sideEffect) {
                // sealed interface라서 종류가 정해져 있음 → 하나라도 빠뜨리면 컴파일 에러
                LoginSideEffect.NavigateToHome -> onLoginSuccess()
                // 홈 이동 → 밖(NavHost)에 성공 알리기 → NavHost가 Home으로 navigate
                is LoginSideEffect.ShowToast -> {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                    // 토스트 → context는 Screen에만 있어서 Screen에서 띄움
                    // is = 이 종류야? 확인 → ShowToast는 class라서 is로 확인해야 message를 꺼낼 수 있음
                }
            }
        }
    }

    Column( //전체 세로 배치
        modifier = modifier
            .fillMaxSize() //화면 전체 크기
            .imePadding() //키보드가 올라오면 그만큼 밀어 올리기
            .verticalScroll(rememberScrollState()) //세로 스크롤 가능하게
            .padding(horizontal = 16.dp) //좌우 띄우기
            .padding(top = 60.dp) //위 띄우기
    ) {
        // 제목
        Text(
            text = "이메일로 로그인하기",
            style = B28,
            color = Black
        )

        Spacer(modifier = Modifier.height(40.dp)) //높이 40짜리 빈 공간

        // 이메일 비밀번호 입력
        SoptTextField(
            label = "이메일 주소", //위에 보일 이름
            value = uiState.email,
            // uiState의 email 표시
            onValueChange = viewModel::onEmailChange,
            // 입력 바뀌면 ViewModel 함수 호출
            // viewModel::onEmailChange = { viewModel.onEmailChange(it) } 를 줄인 함수 참조
            placeholder = "abc@email.com", //안내 글자
            isError = uiState.isEmailError,
            // 에러 여부도 uiState에서 가져옴 (계산은 LoginUiState의 get()이 함)
            errorMessage = "올바른 이메일을 입력해주세요." //에러일 때 보일 빨간 글자
        )

        Spacer(modifier = Modifier.height(32.dp)) //32 띄우기

        SoptTextField(
            label = "비밀번호",
            value = uiState.password, // uiState의 password 표시
            onValueChange = viewModel::onPasswordChange, // ViewModel 함수 호출
            placeholder = "6자 이상의 비밀번호",
            isPassword = true, //비밀번호 적으면 가려줘
            isError = uiState.isPasswordError, // uiState에서 가져옴
            errorMessage = "비밀번호는 6자 이상 입력해주세요." //에러일 때 보일 빨간 글자
        )

        Spacer(modifier = Modifier.height(40.dp)) //40 띄우기

        // 로그인 버튼
        Button(
            onClick = {
                viewModel.onLoginClick(registeredEmail, registeredPassword)
                // 로그인 판단은 ViewModel에 맡김
                // 성공/실패 판단 + sideEffect emit은 ViewModel이 하고, Screen은 위 LaunchedEffect에서 처리

            },
            enabled = uiState.isLoginEnabled,
            // 버튼 활성화 여부도 uiState에서 가져옴
            shape = CircleShape, //알약 모양
            colors = ButtonDefaults.buttonColors( //버튼 색 정하는 거
                containerColor = Black, //활성일 때 배경 (검정)
                contentColor = Gray1, //활성일 때 안쪽 글자색
                disabledContainerColor = Gray1, //비활성일 때 배경
                disabledContentColor = Gray3 //비활성일 때 안쪽 글자색
            ),
            contentPadding = PaddingValues(16.dp), //안쪽 여백
            modifier = Modifier.fillMaxWidth() //가로 꽉 채우기
        ) {
            Text(text = "로그인", style = Sb14) //버튼에 로그인 글자
        }

        Spacer(modifier = Modifier.height(20.dp)) //20 띄우기

        // 회원가입 안내
        Row( //가로로 놓기
            horizontalArrangement = Arrangement.spacedBy(12.dp), //사이사이 12 띄워
            modifier = Modifier.align(Alignment.CenterHorizontally) //얘만 가운데 정렬 (align은 Column 안에서만 사용 가능)
        ) {
            Text(text = "아직 계정이 없으신가요?", style = M14, color = Gray3)
            Text(
                text = "회원가입하기",
                style = M14,
                color = Gray6,
                modifier = Modifier.clickable { onRegisterClick() } // "회원가입 눌렸어!" 하고 밖에 알리기
                // 상태 변경 없이 화면 이동만 하는 거라 ViewModel 안 거치고 그대로 둠
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    PlaySoptTheme {
        LoginScreen(
            registeredEmail = "",
            registeredPassword = "",
            onLoginSuccess = {},
            onRegisterClick = {},
            // viewModel은 기본값(viewModel())이 있어서 안 넣어도 됨
        )
    }
}