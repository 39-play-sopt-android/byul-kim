package org.sopt.play.register

import android.util.Patterns

// 회원가입 화면에 필요한 값을 한 객체로 묶은 클래스
// RegisterViewModel.kt 안에 있다가 따로 파일로 분리함
data class RegisterUiState(
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

    // 4칸 다 입력 && 에러 하나도 없으면 버튼 활성화
    val isRegisterEnabled: Boolean
        get() = name.isNotEmpty() && // 이름 입력함
                email.isNotEmpty() && // 이메일 입력함
                password.isNotEmpty() && // 비밀번호 입력함
                passwordConfirm.isNotEmpty() && // 비밀번호 확인 입력함
                !isEmailError && // 이메일 형식 맞음
                !isPasswordError && // 비밀번호 6자 이상
                !isPasswordConfirmError // 비밀번호 확인 일치
}
