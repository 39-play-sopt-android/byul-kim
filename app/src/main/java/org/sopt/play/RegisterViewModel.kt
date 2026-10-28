package org.sopt.play

import android.util.Patterns
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

// 회원가입 화면에 필요한 값을 한 객체로 묶은 클래스
data class RegisterUiState(
    //괄호 안이 생성자
    val name: String = "", // 이름 입력값
    val email: String = "", // 이메일 입력값
    val password: String = "", // 비밀번호 입력값
    val passwordConfirm: String = "", // 비밀번호 확인 입력값
) {
    // 입력값으로 계산되는 값 → get()으로 읽을 때마다 계산 (따로 저장 안 해서 값 불일치 없음)
    val isEmailError: Boolean
        get() = email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    // 입력했는데 이메일 형식 아니면 에러

    val isPasswordError: Boolean
        get() = password.isNotEmpty() && password.length < 6
    // 입력했는데 6자 미만이면 에러

    val isPasswordConfirmError: Boolean
        get() = passwordConfirm.isNotEmpty() && passwordConfirm != password
    // 입력했는데 비밀번호랑 다르면 에러

    val isRegisterEnabled: Boolean
        get() = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && passwordConfirm.isNotEmpty() && !isEmailError && !isPasswordError && !isPasswordConfirmError
    // 4칸 다 입력 && 에러 하나도 없으면 버튼 활성화
}

// 회원가입 화면 SideEffect (한 번만 처리되는 이벤트)
sealed interface RegisterSideEffect { // interface : 이런 종류야라는 타입만 정하는 틀 그 자체로는 객체 못 만듦
    data class ShowToast(val message: String) : RegisterSideEffect
    // 토스트 → 띄울 메시지를 같이 전달

    data class NavigateToLogin(val email: String, val password: String) : RegisterSideEffect
    // 로그인 화면으로 이동 → NavHost에 가입 정보 넘겨야 해서 email, password를 같이 전달
}

// class RegisterViewModel: 회원가입 화면의 ViewModel 클래스
class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    // 수정 가능한 상태 (ViewModel 내부에서만 변경 → 상태 캡슐화)

    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()
    // Screen에는 읽기 전용으로 공개

    private val _sideEffect = MutableSharedFlow<RegisterSideEffect>()

    // 한 번만 전달할 이벤트용 (초기값 없음, 재구독 시 다시 안 받음)
    val sideEffect: SharedFlow<RegisterSideEffect> = _sideEffect.asSharedFlow()

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
        // 현재 상태 복사 + name만 교체한 새 객체로 업데이트
        // update는 여러 곳에서 동시에 값을 바꾸려 해도 값이 꼬이지 않게 처리햐쥼
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun onPasswordConfirmChange(passwordConfirm: String) {
        _uiState.update { it.copy(passwordConfirm = passwordConfirm) }
    }

    fun onRegisterClick() {
        val state = _uiState.value
        // 현재 상태 꺼내기

        viewModelScope.launch {
            // emit은 suspend 함수라 코루틴 안에서 호출
            _sideEffect.emit(RegisterSideEffect.ShowToast("회원가입이 완료됐어요."))
            _sideEffect.emit(RegisterSideEffect.NavigateToLogin(state.email, state.password))
            // 가입 완료 토스트 → 입력한 이메일/비번 들고 로그인 화면으로
        }
    }
}