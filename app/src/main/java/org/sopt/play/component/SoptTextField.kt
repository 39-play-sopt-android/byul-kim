package org.sopt.play.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.sopt.play.ui.theme.Gray2
import org.sopt.play.ui.theme.Gray5
import org.sopt.play.ui.theme.Gray6
import org.sopt.play.ui.theme.M14
import org.sopt.play.ui.theme.M18
import org.sopt.play.ui.theme.Red
import org.sopt.play.ui.theme.Sb16
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

// 라벨 + 입력칸을 묶은 부품
@Composable
fun SoptTextField(
    label: String, //라벨 글자
    value: String, //지금 값
    onValueChange: (String) -> Unit, //글자를 받아 무언가 하는 함수
    placeholder: String, //안내 글자
    modifier: Modifier = Modifier,
    isPassword: Boolean = false, //비밀번호 칸인지 (안 주면 false)
    isError: Boolean = false, //에러 상태인지 (안 주면 false)
    errorMessage: String = "" //에러일 때 보여줄 글자 (안 주면 빈 글자)
) {
    var isFocused by remember { mutableStateOf(false) }
    //isFocused 포커스 상태인가?
    //mutableStateOf(false) 처음엔 false (안 누른 상태) 바뀌면 화면 다시 그림
    val borderColor = when {
        isError -> Red //에러면 빨강
        isFocused -> Gray5 //에러 아니고 입력 중
        else -> Gray2 //둘 다 아닐 때
    }
    //when 여러 개 중 하나 고르기 (if else는 둘 중 하나)
    //위에서부터 차례로 검사해서 처음 맞는 걸 고름
    //isError를 맨 위에 둬서 입력 중이어도 에러면 빨강이 이김

    Column(modifier = modifier) {
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
            textStyle = M18.copy(color = Gray5), //M18을 복사하되, 색만 Gray5으로 바꾼 새 스타일 만들기
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            // 비밀번호 가리기
            //isPassword가 true → PasswordVisualTransformation() → •••
            //false → VisualTransformation.None → 그대로
            modifier = Modifier //입력칸 테두리, 여백 (얜 대문자인거.. 헷갈려요)
                .fillMaxWidth() //너비 꽉 채우기
                .onFocusChanged {
                    isFocused = it.isFocused
                } //.onFocusChanged { } 포커스 바뀔때 마다 괄호 안 실행
                // it 바뀐 포커스 정보 it.isFocused    지금 포커스 상태인지 (true/false)
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