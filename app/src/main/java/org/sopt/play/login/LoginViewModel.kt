package org.sopt.play.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// LoginUiState(화면 상태)는 LoginUiState.kt로 분리함
// → 이 파일에는 이벤트(SideEffect)랑 로직(ViewModel)만 남김

// ① LoginSideEffect = ViewModel이 Screen에게 보내는 약속된 명령
// 왜 따로 만듦? 토스트엔 context(화면 정보)가 필요한데 ViewModel은 화면보다 오래 살아서
// context를 들고 있으면 옛날 화면이 메모리에서 안 지워짐
// → ViewModel은 명령만 보내고, 실제로 띄우는 건 context 가진 Screen이 함
sealed interface LoginSideEffect {
    // sealed = 여기 적힌 것 말고는 명령 종류 추가 못 함 (딱 이 두 개로 고정)
    // 나중에 Screen에서 when으로 나눌 때 하나 빠뜨리면 안드로이드 스튜디오가 알려줌

    data object NavigateToHome : LoginSideEffect
    // 홈으로 가! 명령
    // 들고 갈 정보가 없어서 object (딱 하나만 존재하면 됨)

    data class ShowToast(val message: String) : LoginSideEffect
    // 토스트 띄워! 명령
    // 무슨 말을 띄울지 message를 들고 가야 해서 class
}

// ② LoginViewModel = 상태 보관 + 로직 담당
class LoginViewModel : ViewModel() {
    // : ViewModel() → ViewModel을 물려받음 → 화면 돌려도 안 사라지는 능력 생김

    private val _uiState = MutableStateFlow(LoginUiState())
    // 상태가 흐르는 파이프. 처음 값은 LoginUiState() = 빈 이메일, 빈 비밀번호 상자
    // LoginUiState는 다른 파일(LoginUiState.kt)에 있지만 같은 패키지라 import 없이 바로 씀
    // private → ViewModel 안에서만 수정 가능 (Screen은 손 못 댐 = 상태 캡슐화)
    // 이름 앞에 _ → 이건 내부용 원본이야라는 안드로이드 개발자들 약속

    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    // 위 파이프를 읽기 전용으로 바꿔서 공개 → Screen은 이걸 보기만 함
    // Screen이 바꾸고 싶으면 아래 함수(onEmailChange 등)를 불러서 부탁해야 함 = UDF

    private val _sideEffect = MutableSharedFlow<LoginSideEffect>()
    // 명령 보내는 파이프. 처음 값 없음 (명령은 보낼 때만 생기니까)
    // StateFlow가 아니라 SharedFlow인 이유:
    // StateFlow는 마지막 값을 계속 들고 있어서 화면 돌리면 토스트가 또 뜸
    // SharedFlow는 보내고 나면 끝 → 명령은 딱 한 번만 실행돼야 하니까 이게 맞음

    val sideEffect: SharedFlow<LoginSideEffect> = _sideEffect.asSharedFlow()
    // 이것도 읽기 전용으로 공개 → Screen은 명령을 듣기만 함

    // Screen: 이메일 칸 글자 바뀜 새 글자는 이거임
    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
        // it = 지금 상태 상자
        // it.copy(email = email) = 지금 상자 복사하는데 email만 새 걸로 바꾼 새 상자
        // update { } = 그 새 상자로 교체 (여러 곳에서 동시에 바꿔도 안 꼬이게 안전하게)
        // 왜 it.email = email 안 함? → StateFlow는 상자가 바뀌어야 변한 걸 알고 화면을 다시 그림
    }

    // Screen: 비밀번호 칸 글자 바뀜
    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
        // 위랑 똑같이 password만 바꾼 새 상자로 교체
    }

    // Screen: 로그인 버튼 눌림 회원가입 정보는 이거임
    // 회원가입 정보는 MainNavHost가 갖고 있어서 버튼 누를 때 같이 받아옴
    fun onLoginClick(registeredEmail: String, registeredPassword: String) {
        val state = _uiState.value
        // .value = 파이프에 지금 들어있는 최신 상태 꺼내기

        val isSuccess = registeredEmail.isNotEmpty() &&
                state.email == registeredEmail &&
                state.password == registeredPassword
        // 회원가입한 적 있고 && 이메일 같고 && 비밀번호 같으면 → 성공(true)

        viewModelScope.launch {
            // emit(보내기)은 suspend 함수 = 받는 쪽 준비될 때까지 기다릴 수 있는 함수
            // suspend 함수는 코루틴 안에서만 부를 수 있어서 launch { }로 코루틴을 열어줌
            // viewModelScope = ViewModel 사라질 때(onCleared) 자동으로 같이 정리됨 → 직접 안 꺼도 됨
            if (isSuccess) {
                _sideEffect.emit(LoginSideEffect.ShowToast("로그인에 성공했어요."))
                // 성공 토스트 띄우라고 명령 보냄
                // 글자는 그냥 데이터라 ViewModel에 있어도 괜찮음 (문제는 context지 글자가 아님)
                _sideEffect.emit(LoginSideEffect.NavigateToHome)
                // 홈으로 가라고 명령 보냄
            } else {
                _sideEffect.emit(LoginSideEffect.ShowToast("이메일 또는 비밀번호가 올바르지 않아요."))
                // 실패 → 실패 토스트 명령만 보냄
            }
        }
    }
}
