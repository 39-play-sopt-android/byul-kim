package org.sopt.play.register

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

// 회원가입 화면 SideEffect (한 번만 처리되는 이벤트)
sealed interface RegisterSideEffect { // interface : 이런 종류야라는 타입만 정하는 틀 그 자체로는 객체 못 만듦
    data class ShowToast(val message: String) : RegisterSideEffect
    // 토스트 → 띄울 메시지를 같이 전달

    data class NavigateToLogin(
        val name: String,
        val email: String,
        val password: String,
    ) : RegisterSideEffect
    // 로그인 화면으로 이동 → NavHost에 가입 정보 넘겨야 해서 이름, 이메일, 비밀번호를 같이 전달
}

// class RegisterViewModel: 회원가입 화면의 ViewModel 클래스
class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    // 수정 가능한 상태 (ViewModel 내부에서만 변경 → 상태 캡슐화)
    // RegisterUiState는 다른 파일에 있지만 같은 패키지라 import 없이 바로 씀

    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()
    // Screen에는 읽기 전용으로 공개

    private val _sideEffect = MutableSharedFlow<RegisterSideEffect>()
    // 한 번만 전달할 이벤트용 (초기값 없음, 재구독 시 다시 안 받음)

    val sideEffect: SharedFlow<RegisterSideEffect> = _sideEffect.asSharedFlow()
    // Screen에는 읽기 전용으로 공개 → 듣기만 함

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
        // 현재 상태 복사 + name만 교체한 새 객체로 업데이트
        // update는 여러 곳에서 동시에 값을 바꾸려 해도 값이 꼬이지 않게 처리해줌
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

        viewModelScope.launch {
            _sideEffect.emit(RegisterSideEffect.ShowToast("회원가입이 완료됐어요."))
            _sideEffect.emit(
                RegisterSideEffect.NavigateToLogin(
                    name = state.name,
                    email = state.email,
                    password = state.password,
                ),
            )
        }
    }
}
