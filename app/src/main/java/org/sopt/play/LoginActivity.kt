package org.sopt.play

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.util.Patterns
import org.sopt.play.ui.theme.PlaySoptTheme
import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import org.sopt.play.component.SoptTextField
import org.sopt.play.ui.theme.B28
import org.sopt.play.ui.theme.Black
import org.sopt.play.ui.theme.Gray1
import org.sopt.play.ui.theme.Gray3
import org.sopt.play.ui.theme.Gray6
import org.sopt.play.ui.theme.M14
import org.sopt.play.ui.theme.Sb14
import org.sopt.play.ui.theme.White

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

    val context = LocalContext.current //지금 화면 정보 (화면 이동, 토스트에 필요)

    var registeredEmail by remember { mutableStateOf("") }
    var registeredPassword by remember { mutableStateOf("") }
    //회원가입에서 받아 온 정보를 기억하는 상자 2개

    val registerLauncher = rememberLauncherForActivityResult(
        //다른 화면을 열고, 그 화면이 닫히면 답장을 받는 장치를 만들어서 registerLauncher 담음
        ActivityResultContracts.StartActivityForResult()
        //화면을 열고 결과를 받는다 (우편함 종류)
    ) { result ->
        //회원가입 화면이 닫히고 돌아오면 여기가 실행됨 (답장 우편함)
        if (result.resultCode == Activity.RESULT_OK) { //성공 표시가 붙어 왔으면
            registeredEmail = result.data?.getStringExtra("email") ?: ""
            registeredPassword = result.data?.getStringExtra("password") ?: ""
            //상자에서 email, password 이름표 붙은 걸 꺼내서 기억
            //?. 와 ?: "" 는 상자가 비어 있으면(null) 빈 글자를 쓰라는 안전장치
        }
    }

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
            value = email, // email 상자 안 값을 보여줌
            onValueChange = { email = it }, //글자 바뀌면 email 상자에 넣어
            //it은 사용자가 방금 친 결과 글자 그걸 이메일 안에 넣으라는 거
            //onValueChange = { newText -> email = newText } 이렇게 써도 된다는데 어떤걸로 쓰는게 더 좋을까요
            placeholder = "abc@email.com", //안내 글자
            isError = isEmailError, //에러인지 알려줌
            errorMessage = "올바른 이메일을 입력해주세요." //에러일 때 보일 빨간 글자

        )

        Spacer(modifier = Modifier.height(32.dp)) //32 띄우기

        SoptTextField(
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
            onClick = { //누르면 할 일
                if (registeredEmail.isNotEmpty() && email == registeredEmail && password == registeredPassword) {
                    //회원가입한 적 있고 && 이메일 같고 && 비밀번호 같으면 → 로그인 성공
                    val intent = Intent(context, MainActivity::class.java).apply {
                        // 메인화면으로 가는 요청서 만들기
                        // 출발: 지금 화면(context), 도착: MainActivity. .apply { }는 요청서에 옵션을 덧붙이는 자리
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    // 로그인 화면 기록 지우기 → 메인에서 뒤로가기 하면 앱 꺼짐
                    }
                    context.startActivity(intent) //메인 화면으로 이동
                } else {
                    //하나라도 다르면 → 실패 토스트
                    Toast.makeText(context, "이메일 또는 비밀번호가 올바르지 않아요.", Toast.LENGTH_SHORT).show()
                } //LENGTH_SHORT → 약 2초 .show() → 보여주기
            },
            enabled = isLoginEnabled, //조건 맞으면 켜짐, 아니면 꺼짐
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
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            //horizontalArrangement 가로 방향으로 배치
            //spaced~ 사이사이 12 띄워
            modifier = Modifier.align(Alignment.CenterHorizontally) //얘만 가운데 정령
        ) { // Modifier.align 나 하나만 정렬할게! 이런 느낌 align은 Column 안에서만 사용 가능
            Text(text = "아직 계정이 없으신가요?", style = M14, color = Gray3)
            Text(
                text = "회원가입하기",
                style = M14,
                color = Gray6,
                modifier = Modifier.clickable {
                    registerLauncher.launch(Intent(context, RegisterActivity::class.java))
                    //intent ~ (java) 회원가입 화면으로 가는 요청서
                    //register~ 우편함을 통해 열기
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    PlaySoptTheme {
        LoginScreen()
    }
}