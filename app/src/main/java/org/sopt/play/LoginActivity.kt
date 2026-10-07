package org.sopt.play

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.focus.onFocusChanged
import android.util.Patterns
import org.sopt.play.ui.theme.PlaySoptTheme

// ───── 피그마 색깔 ─────
private val Black = Color(0xFF121212)
//private 이 파일 안에서 쓴다, val 안 바뀌는거
private val Gray1 = Color(0xFFF7F7F7)
private val Gray2 = Color(0xFFD1D5D6)
private val Gray3 = Color(0xFFB2BABD)
private val Gray5 = Color(0xFF505559)
private val Gray6 = Color(0xFF23272A)
private val White = Color(0xFFFFFFFF)
private val Red = Color(0xFFFF4D4D)

// ───── 피그마 글꼴 ─────
private val Pretendard = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold),
    Font(R.font.pretendard_medium, FontWeight.Medium)
)
//R 프로젝트, res 폴더의 주소록

private val B28 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Bold,
    fontSize = 28.sp,
    lineHeight = 33.6.sp,
    letterSpacing = (-0.28).sp //자간
)

private val Sb16 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
    lineHeight = 19.2.sp,
    letterSpacing = (-0.16).sp
)

private val M18 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp,
    lineHeight = 21.6.sp,
    letterSpacing = (-0.18).sp
)

private val Sb14 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp,
    lineHeight = 16.8.sp,
    letterSpacing = (-0.14).sp
)

private val M14 = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 16.8.sp,
    letterSpacing = (-0.14).sp
)

class LoginActivity : ComponentActivity() {
    //LoginActivity 방 만들기 ComponentActivity 기능 사용
    override fun onCreate(savedInstanceState: Bundle?) {
        //onCreate 화면이 처음 만들어질 때 (생명주기) override 원래 있는 onCreate 고쳐쓰기
        //saved~Bundle? 화면이 다시 만들어질 때 이전 상태를 담아 오는 상자 ?는 비어 있을(null) 수도 있다는 뜻
        super.onCreate(savedInstanceState) //super 부모
        enableEdgeToEdge() //화면을 상태바, 하단바까지 꽉 채움
        setContent { //안에 쓴 Compose 코드가 화면에 그려짐
            PlaySoptTheme { //앱 테마
                Scaffold(containerColor = White) { innerPadding ->
                    //Scaffold 화면 뼈대 con~white 배경색
                    //Scaffold가 상태바에 안 가리려면 이만큼 띄워 하고 여백 값 넘겨주는 거
                    LoginScreen(modifier = Modifier.padding(innerPadding))
                } //아래 LoginScreen을 불러서 그리고 받은 여백을 꾸밈으로 넘기기
            }
        }
    }
}

@Composable //@Composable: 화면 그리는 함수 표시
fun LoginScreen(modifier: Modifier = Modifier) {
    //modifier: Modifier = Modifier: 밖에서 꾸밈을 받고, 안 주면 빈 꾸밈
    var email by remember { mutableStateOf("") } //var는 바뀌는 상자
    var password by remember { mutableStateOf("") }
    //email, password는 상자 이름
    //mutableStateOf("") 처음 값은 빈 글자고 값이 바뀌면 Compose가 화면을 다시 그림
    //remember { } 다시 그려도 값을 잊지 않음
    //by는 email.value 대신 그냥 email로 쓰게 해줌

    val isEmailError = email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    //뭔가 쳤는데(isNotEmpty) 그리고(&&) 이메일 모양이 아니면(!~matches) → 에러(true)
    //isNotEmpty() 비어 있지 않으면 true → 아무것도 안 쳤을 땐 에러 안 띄우려고
    //Patterns.EMAIL_ADDRESS.matcher(email).matches() email이 이메일 모양이면 true
    //! 는 반대로 뒤집기 (true ↔ false)

    val isPasswordError = password.isNotEmpty() && password.length < 6
    //뭔가 쳤는데 그리고 6글자보다 적으면 → 에러(true)
    //password.length 글자 수 (•••로 가려져도 진짜 글자 수로 셈)

    val isLoginEnabled = email.isNotEmpty() && password.isNotEmpty() && !isEmailError && !isPasswordError
    //이메일 칸 비어있지 않고 && 비밀번호 칸 비어있지 않고 && 이메일 에러 아니고 && 비밀번호 에러 아니면 → true (버튼 켜짐)
    //하나라도 아니면 → false (버튼 꺼짐)

    Column( //전체 세로 배치
        modifier = modifier
            //modifier (소문자) 받아 온 꾸밈(상태바 여백)부터 적용? 이거 대문자 소문자가 헷갈려요....
            .fillMaxSize() //화면 전체 크기
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
        LoginTextField(
            label = "이메일 주소", //위에 보일 이름
            value = email, // email 상자 안 값을 보여줌
            onValueChange = { email = it }, //글자 바뀌면 email 상자에 넣어
            //it은 사용자가 방금 친 결과 글자 그걸 이메일 안에 넣으라는 거
            //onValueChange = { newText -> email = newText } 이렇게 써도 된다는데 어떤걸로 쓰는게 더 좋을까요
            placeholder = "abc@email.com", //안내 글자
            isError = isEmailError, //에러인지 알려줌
            errorMessage = "올바른 이메일을 입력해주세요." //에러일 때 보일 빨간 글자

        )

        Spacer(modifier = Modifier.height(32.dp)) //32 띄우기

        LoginTextField(
            label = "비밀번호",
            value = password,
            onValueChange = { password = it },
            placeholder = "6자 이상의 비밀번호",
            isPassword = true, //비밀번호 적으면 가려줘
            isError = isPasswordError, //에러인지 알려줌
            errorMessage = "비밀번호는 6자 이상 입력해주세요." //에러일 때 보일 빨간 글자
        )

        Spacer(modifier = Modifier.height(40.dp)) //40 띄우기

        // 로그인 버튼
        Button(
            onClick = { }, //누르면 할 일
            enabled = isLoginEnabled, //조건 맞으면 켜짐, 아니면 꺼짐
            shape = CircleShape, //알약 모양
            colors = ButtonDefaults.buttonColors( //버튼 색 정하는 거
                containerColor = Black, //활성일 때 배경 (검정)
                contentColor = White, //활성일 때 안쪽 글자색 (흰색)
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
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            //horizontalArrangement 가로 방향으로 배치
            //spaced~ 사이사이 12 띄워
            modifier = Modifier.align(Alignment.CenterHorizontally) //얘만 가운데 정령
        ) { // Modifier.align 나 하나만 정렬할게! 이런 느낌 align은 Column 안에서만 사용 가능
            Text(text = "아직 계정이 없으신가요?", style = M14, color = Gray3)
            Text(text = "회원가입하기", style = M14, color = Gray6)
        }
    }
}

// 라벨 + 입력칸을 묶은 부품
@Composable
fun LoginTextField(
    label: String, //라벨 글자
    value: String, //지금 값
    onValueChange: (String) -> Unit, //글자를 받아 무언가 하는 함수
    placeholder: String, //안내 글자
    isPassword: Boolean = false, //비밀번호 칸인지 (안 주면 false)
    isError: Boolean = false, //에러 상태인지 (안 주면 false)
    errorMessage: String = "" //에러일 때 보여줄 글자 (안 주면 빈 글자)
) {
    var isFocused by remember { mutableStateOf(false) }
    //isFocused 포커스 상태인가?
    //mutableStateOf(false)	처음엔 false (안 누른 상태) 바뀌면 화면 다시 그림
    val borderColor = when {
        isError -> Red //에러면 빨강
        isFocused -> Gray5 //에러 아니고 입력 중
        else -> Gray2 //둘 다 아닐 때
    }
    //when 여러 개 중 하나 고르기 (if else는 둘 중 하나)
    //위에서부터 차례로 검사해서 처음 맞는 걸 고름
    //isError를 맨 위에 둬서 입력 중이어도 에러면 빨강이 이김

    Column {
        Text(
            text = label, //이메일 주소, 비밀번호 글자
            style = Sb16,
            color = Gray6,
            modifier = Modifier.padding(start = 8.dp) //왼쪽만 8띄움
        )

        Spacer(modifier = Modifier.height(6.dp)) //높이 6 띄우기

        BasicTextField( //입력칸 본체
            value = value, //입력칸에 이 값을 보여줘
            onValueChange = onValueChange, //글자가 바뀌면 이 일을 해
            singleLine = true, //최대 한 줄로만 노출
            textStyle = M18.copy(color = Gray6), //M18을 복사하되, 색만 Gray6으로 바꾼 새 스타일 만들기
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            // 비밀번호 가리기
            //isPassword가 true → PasswordVisualTransformation() → •••
            //false → VisualTransformation.None → 그대로
            modifier = Modifier //입력칸 테두리, 여백 (얜 대문자인거.. 헷갈려요)
                .fillMaxWidth() //너비 꽉 채우기
                .onFocusChanged { isFocused = it.isFocused } //.onFocusChanged { } 포커스 바뀔때 마다 괄호 안 실행
                // it 바뀐 포커스 정보 it.isFocused	지금 포커스 상태인지 (true/false)
                .border(width = 2.dp, color = borderColor, shape = RoundedCornerShape(12.dp))
                //borderColor 상황에 따라 바뀌는 거
                .padding(16.dp),
            decorationBox = { innerTextField -> //안내 글자 넣기
                Box { //안내 글자와 진짜 입력 부분 같은 자리에 겹쳐놓기
                    if (value.isEmpty()) { //값이 비어 있으면 이렇게 안내~
                        Text(text = placeholder, style = M18, color = Gray2)
                    }
                    innerTextField() //진짜 입력 부분을 그림
                }
            }
        )
        if (isError) { //에러일 때만 아래를 그림
            Spacer(modifier = Modifier.height(6.dp))  //입력칸과 에러 메시지 사이 6 띄우기
            Text(
                text = errorMessage, //받아 온 에러 글자
                style = M14,
                color = Red,
                modifier = Modifier.padding(start = 8.dp) //왼쪽 8 띄우기
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    PlaySoptTheme {
        LoginScreen()
    }
}