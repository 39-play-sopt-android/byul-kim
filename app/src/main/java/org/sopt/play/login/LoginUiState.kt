package org.sopt.play.login

import android.util.Patterns

// 로그인 화면에 보이는 값을 한 상자에 묶은 것
// data class = 값 담는 용도의 클래스, copy() 함수가 자동으로 생김
// LoginViewModel.kt 안에 있다가 따로 파일로 분리함
// → 파일 이름만 보고 "로그인 화면 상태"가 어디 있는지 바로 찾을 수 있게
data class LoginUiState(
    val email: String = "", // 이메일 칸에 적힌 글자 (처음엔 빈 글자)
    val password: String = "", // 비밀번호 칸에 적힌 글자 (처음엔 빈 글자)
    // val(못 바꾸는 값)인 이유: 안의 값을 직접 고치는 게 아니라 새 상자로 통째로 교체할 거라서
) {
    // 여기부터는 email, password만 있으면 "계산"할 수 있는 값들
    // get() = ... → 이 값을 읽을 때마다 그때그때 계산해서 돌려줌
    // 저장 안 하고 계산하는 이유: email만 바꿔도 에러 여부가 자동으로 맞춰져서 값이 어긋날 일이 없음

    val isEmailError: Boolean
        get() = email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    // 뭔가 쳤는데(isNotEmpty) && 이메일 모양이 아니면(!~matches) → 에러(true)

    val isPasswordError: Boolean
        get() = password.isNotEmpty() && password.length < 6
    // 뭔가 쳤는데 && 6글자보다 적으면 → 에러(true)

    // 이메일, 비밀번호 다 입력 && 에러 둘 다 없으면 → 버튼 켜짐(true)
    val isLoginEnabled: Boolean
        get() = email.isNotEmpty() && // 이메일 칸 비어있지 않음
                password.isNotEmpty() && // 비밀번호 칸 비어있지 않음
                !isEmailError && // 이메일 형식 맞음
                !isPasswordError // 비밀번호 6자 이상
}
