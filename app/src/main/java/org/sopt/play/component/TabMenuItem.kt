package org.sopt.play.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.ui.theme.Black
import org.sopt.play.ui.theme.Gray3
import org.sopt.play.ui.theme.M18
import org.sopt.play.ui.theme.PlaySoptTheme


//메뉴 탭 만들기
@Composable
fun TabMenuItem(
    text: String,              // 탭 이름
    isSelected: Boolean,       // 선택됐는지 (밖에서 알려줌)
    onClick: () -> Unit,       // 눌렸을 때 할 일 (밖에서 정함)
    modifier: Modifier = Modifier,
){
    Column( // 점 위, 글자 아래로 세로 배치
        modifier = modifier
            .clickable(onClick = onClick) // 누르면 onClick 실행
            //앞의 onClick = clickable의 칸 이름
            //뒤의 onClick = TabMenuItem이 밖에서 받은 함수
            .padding(horizontal = 16.dp, vertical = 8.dp),   // 피그마: 좌우 16, 위아래 8
        verticalArrangement = Arrangement.spacedBy(4.dp),    // 피그마: 점과 글자 사이 4
        horizontalAlignment = Alignment.CenterHorizontally,  // 점을 글자 가운데에
    ){
        // 선택 표시 점
        Box(
            modifier = Modifier
                .size(4.dp)
                .background(
                    color = if (isSelected) Black else Color.Transparent,
                    // 선택됐으면 검정, 아니면 투명 (안 보이지만 자리는 차지 → 글자 높이가 안 흔들림)
                    shape = CircleShape,   // 동그랗게
                )


        )

        Text(
            text = text,
            style = M18,
            color = if (isSelected) Black else Gray3,  // 선택: 검정, 기본: 회색
        )
    }
}



@Preview(showBackground = true)
@Composable
private fun TabMenuItemComparePreview() {
    PlaySoptTheme {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            TabMenuItem(text = "메뉴", isSelected = true, onClick = {})
            TabMenuItem(text = "메뉴", isSelected = false, onClick = {})
        }
    }
}