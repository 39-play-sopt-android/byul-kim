package org.sopt.play

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

// RegisterUiState(화면 상태)는 RegisterUiState.kt로 분리함
// → 이 파일에는 이벤트(SideEffect)랑 로직(ViewModel)만 남김

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

    // 회원가입 "버튼 클릭" 처리 → 진짜 클릭이라 이름이 onRegisterClick 그대로
    // (Screen 파라미터 onRegisterSuccess는 가입 성공 후에 불리는 다른 함수)
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
