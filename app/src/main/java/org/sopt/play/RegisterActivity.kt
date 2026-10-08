package org.sopt.play

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.ui.theme.PlaySoptTheme

class RegisterActivity : ComponentActivity() { //RegisterActivity 화면 만들기
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaySoptTheme { //테마 입히고 흰 배경 뼈대 만듦
                Scaffold(containerColor = White) { innerPadding ->
                    RegisterScreen(
                        modifier = Modifier.padding(innerPadding),
                        onRegisterClick = { email, password ->
                            //회원가입 버튼을 누르면 여기가 실행됨
                            val resultIntent = Intent().apply { //빈 상자 만들기
                                putExtra("email", email)       //상자에 이메일 넣기
                                putExtra("password", password) //상자에 비밀번호 넣기
                            }
                            setResult(RESULT_OK, resultIntent) //성공 표시와 함께 상자 보내기
                            finish()  //회원가입 화면 닫기 → 로그인 화면으로 돌아감
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onRegisterClick: (String, String) -> Unit = { _, _ -> }
    //onRegisterClick 버튼 누르면 할 일을 밖에서 받음. (String, String) 이메일, 비밀번호 2개를 넘겨줌
    // -> Unit 돌려주는건 없음
    //= { _, _ -> } 는 기본값: 아무것도 안 함 (Preview용)
    ) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }
    //입력값 상자 4개 (이름, 이메일, 비밀번호, 비밀번호 확인)

    val isEmailError = email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordError = password.isNotEmpty() && password.length < 6
    //로그인이랑 똑같음 쳤는데 이메일 모양 아니면 에러, 6글자보다 적으면 에러

    val isPasswordConfirmError = passwordConfirm.isNotEmpty() && passwordConfirm != password
    //쳤는데 비밀번호랑 다르면 에러

    val isRegisterEnabled = name.isNotEmpty() &&
            email.isNotEmpty() && password.isNotEmpty() && passwordConfirm.isNotEmpty() &&
            !isEmailError && !isPasswordError && !isPasswordConfirmError
    //4칸 다 쳤고 && 에러가 하나도 없으면 → 버튼 켜짐

    Column( //세로 배치
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 60.dp)
    ) {
        Text(
            text = "이메일로 회원가입",
            style = B28,
            color = Black
        )

        Spacer(modifier = Modifier.height(40.dp))

        //LoginTextField 재사용! (private 지워서 여기서도 쓸 수 있음)
        LoginTextField(
            label = "이름",
            value = name,
            onValueChange = { name = it },
            placeholder = "홍길동"
        )

        Spacer(modifier = Modifier.height(32.dp))

        LoginTextField(
            label = "이메일 주소",
            value = email,
            onValueChange = { email = it },
            placeholder = "abc@email.com",
            isError = isEmailError,
            errorMessage = "올바른 이메일을 입력해주세요."
        )

        Spacer(modifier = Modifier.height(32.dp))

        LoginTextField(
            label = "비밀번호",
            value = password,
            onValueChange = { password = it },
            placeholder = "6자 이상의 비밀번호",
            isPassword = true,
            isError = isPasswordError,
            errorMessage = "비밀번호는 6자 이상 입력해주세요."
        )

        Spacer(modifier = Modifier.height(32.dp))

        LoginTextField(
            label = "비밀번호 확인",
            value = passwordConfirm,
            onValueChange = { passwordConfirm = it },
            placeholder = "6자 이상의 비밀번호",
            isPassword = true,
            isError = isPasswordConfirmError,
            errorMessage = "비밀번호와 동일하게 입력해주세요."
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { onRegisterClick(email, password) }, //입력한 이메일 , 비밀번호를 밖으로 넘김
            enabled = isRegisterEnabled, //조건이 맞으면 켜짐
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Black,
                contentColor = White,
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

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    PlaySoptTheme {
        RegisterScreen()
    }
}